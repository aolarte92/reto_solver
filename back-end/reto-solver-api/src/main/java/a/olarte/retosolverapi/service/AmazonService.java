package a.olarte.retosolverapi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

@Service
public class AmazonService {

	private AmazonS3 s3client;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;
	
	
	

	@PostConstruct
	private void initializeAmazon() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_WEST_1)
				.build();
	}
	
	
	
	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_").replaceAll("[^a-zA-Z0-9-_.]", "");
	}
	
	
	private void uploadFileTos3bucket(String fileName, MultipartFile mpf) throws IOException {
		s3client.putObject(new PutObjectRequest(bucketName, fileName, mpf.getInputStream(), new ObjectMetadata())
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}

	private void uploadFilePart(String fileName, MultipartFile mpf) {
		long contentLength = mpf.getSize();
		long partSize = 5 * 1024 * 1024; // set part size 5 MB

		try {
			// Create a list of ETag objects. You retrieve ETags for each object part
			// uploaded,
			// then, after each individual part has been uploaded, pass the list of ETags to
			// the request to complete the upload.
			List<PartETag> partETags = new ArrayList<PartETag>();

			// Initiate the multipart upload.
			InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, fileName);
			InitiateMultipartUploadResult initResponse = s3client.initiateMultipartUpload(initRequest);
			// Upload the file parts.
			long filePosition = 0;
			for (int i = 1; filePosition < contentLength; i++) {
				// Because the last part could be less than 5 MB, adjust the part size as
				// needed.
				partSize = Math.min(partSize, (contentLength - filePosition));
				// Create the request to upload a part.
				UploadPartRequest uploadRequest = new UploadPartRequest().withBucketName(bucketName).withKey(fileName)
						.withUploadId(initResponse.getUploadId()).withPartNumber(i).withFileOffset(filePosition)
						.withInputStream(mpf.getInputStream()).withPartSize(partSize);
				// Upload the part and add the response's ETag to our list.
				UploadPartResult uploadResult = s3client.uploadPart(uploadRequest);
				partETags.add(uploadResult.getPartETag());
				filePosition += partSize;
			}
			// Complete the multipart upload.
			CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName, fileName,
					initResponse.getUploadId(), partETags);
			s3client.completeMultipartUpload(compRequest);
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public String deleteFileFromS3Bucket(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
		return "Successfully deleted";
	}

	public String uploadFile(MultipartFile multipartFile, String folder) {

		String fileUrl = "";
		try {
			String fileName = folder + generateFileName(multipartFile);
			fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
			uploadFilePart(fileName, multipartFile);
		} catch (Exception e) {
			e.printStackTrace();
			fileUrl = "";
		}
		
		return fileUrl;
	}
	
	public String uploadFile(File file, String fileName, String folder) {
		
		String fileUrl = "";
		try {
			fileName =  folder + fileName;
			fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
			FileInputStream input = new FileInputStream(file);
			
			TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3client).build();

			// TransferManager processes all transfers asynchronously,
			// so this call returns immediately.
			Upload upload = tm.upload(bucketName, fileName, input, new ObjectMetadata());
			System.out.println("Object upload started");

			// Optionally, wait for the upload to finish before continuing.
			upload.waitForCompletion();
			System.out.println("Object upload complete");
			
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		} catch (AmazonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileUrl;
	}

	
	
}
