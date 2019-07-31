import {formTypes} from './../Forms/Containers/FormCreator';


export const formularioTest = () =>{
    return [
        {
            type: formTypes.text,
            label: "Número de documento",
            placeholder: "Ingrese número de documento",
            required: true,
            classContainer: "form-group col-md-12",
            classLabel: "",
            classInput: "form-control",
            variable: "doc",
            // value: '',
            maxLength:20
        },
        {
            type: formTypes.file,
            label: "Seleccione archivo para la prueba",
            required: true,
            classContainer: "form-group col-md-12",
            classLabel: "",
            classInput: "",
            variable: "file",
            // value: '',
        },
        
        
    ]
}

export const formularioHistoriaGeneral = (idform:String) => { 
   
    return [
        
        {
            type: formTypes.text,
            label: "Nombres",
            placeholder: "Ingrese nombres completos",
            required: true,
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control",
            variable: idform+"_nombres",
            // value: '',
        },
        {
            type: formTypes.text,
            label: "Apellidos",
            placeholder: "Ingrese apellidos completos",
            required: false,
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control",
            variable: idform+"_apellidos",
            // value: '',
        },
        select({
            label: "Tipo de Documento",
            placeholder: "Seleccione un tipo de documento",
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control",
            // value: '',
            required: true,
            keyValue:"value",
            keyText:"text",
            variable: idform+"_tipodocumento",
            variableDad:null,
            data: [
                { value:1, text:'Contraseña'},
                { value:2, text:'Cédula de ciudadanía'},
                { value:3, text:'Tarjeta de identidad'},
                { value:4, text:'Cedula de extranjería'},
                { value:5, text:'Pasaporte'},
            ]
        }),
        {
            type: formTypes.text,
            label: "Número de documento",
            placeholder: "Ingrese número de documento",
            required: true,
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control",
            variable: idform+"_ndocuemtno",
            // value: '',
        },
        {
            type: formTypes.text,
            label: "Número de teléfono",
            placeholder: "Ingrese número de telefono y/o celular",
            required: false,
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control",
            variable: idform+"_ntelefono",
            // value: '',
        },
        select({
            label: "Género",
            placeholder: "Seleccione un género",
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control ",
            // value: '',
            required: true,
            keyValue:"value",
            keyText:"text",
            variable: idform+"_genero",
            data: [
                { value:2, text:'Femenino'},
                { value:1, text:'Masculino'},
                { value:3, text:'LGBTIQ'},
            ]
        }),
        {
            type: formTypes.number,
            label: "Edad",
            placeholder: "Ingrese la edad",
            required: false,
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control",
            variable: idform+"_edad",
            // value: '',
        },
        {
            type: formTypes.text,
            label: "Dirección de residencia",
            placeholder: "Ingrese la dirección",
            required: true,
            classContainer: "form-group col-md-12",
            classLabel: "",
            classInput: "form-control",
            variable: idform+"_direccion",
            // value: '',
        },
        select({
            label: "Barrio",
            placeholder: "Seleccione un barrio",
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control ",
            // value: '',
            required: false,
            keyValue:"value",
            keyText:"text",
            variable: idform+"_barrio",
            data: [
                { value:2, text:'barrio1'},
            ]
        }),
        select({
            label: "Comuna",
            placeholder: "Seleccione una comuna",
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control ",
            // value: '',
            required: false,
            keyValue:"value",
            keyText:"text",
            variable: idform+"_comuna",
            data: [
                { value:2, text:'comuna1'},
            ]
        }),
        select({
            label: "Municipio",
            placeholder: "Seleccione un municipio",
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control ",
            // value: '',
            required: false,
            keyValue:"value",
            keyText:"text",
            variable: idform+"_municipio",
            data: [
                { value:2, text:'municipio1'},
            ]
        }),
        select({
            label: "Departamento",
            placeholder: "Seleccione un departamento",
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control ",
            // value: '',
            required: false,
            keyValue:"value",
            keyText:"text",
            variable: idform+"_departamento",
            data: [
                { value:2, text:'departamento1'},
            ]
        }),
        select({
            label: "Zona",
            placeholder: "Seleccione una zona",
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control ",
            // value: '',
            required: true,
            keyValue:"value",
            keyText:"text",
            variable: idform+"_zona",
            data: [
                { value:2, text:'zona1'},
            ]
        }),
        select({
            label: "Escolaridad",
            placeholder: "Seleccione una escolaridad",
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control ",
            // value: '',
            required: false,
            keyValue:"value",
            keyText:"text",
            variable: idform+"_escolaridad",
            data: [
                { value:2, text:'escolaridad1'},
            ]
        }),
        select({
            label: "Ocupación",
            placeholder: "Seleccione una ocupación",
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control ",
            // value: '',
            required: false,
            keyValue:"value",
            keyText:"text",
            variable: idform+"_ocupacion",
            data: [
                { value:2, text:'Ocupacion1'},
            ]
        }),
        {
            type: formTypes.text,
            label: "Correo electrónico",
            placeholder: "Ingrese correo electrónico ",
            required: false,
            classContainer: "form-group col-md-6",
            classLabel: "",
            classInput: "form-control",
            variable: idform+"_email",
            // value: '',
        },
        {
            type: formTypes.text,
            label: "Remitido por",
            placeholder: "Ingrese quen lo remitió",
            required: false,
            classContainer: "form-group col-md-12",
            classLabel: "",
            classInput: "form-control",
            variable: idform+"_remitido",
            // value: '',
        },
        {
            type:  formTypes.textarea,
            label: "Motivo de la Consulta",
            placeholder: "Ingrese el motivo",
            required: false,
            classContainer: "form-group col-md-12",
            classLabel: "",
            classInput: "form-control",
            variable: idform+"_motivo",
            rows:5,
            // value:""
        },
       
    ]
   
    
};

export const select = (config:{}) =>{
    return  {
        type: formTypes.select,
        ...config
    }
    
}

export const calendar = (variable:String, value:Object = new Date(), dateFormat:String = "dd/MM/yyyy") =>{
    return [
        {
            type:  formTypes.calendar,
            label: "Fecha Regsitro",
            placeholder: dateFormat,
            required: true,
            classContainer: "form-group col-md-12 ",
            classLabel: "",
            classInput: "form-control ",
            variable,
            value,
            dateFormat,
            minDate: new Date(),
        }, 
    ]
}

export const pregunta = (variable:String, label:String, idSi:String, idNo:String) => {
    return [
        {
            type:  formTypes.question,
            label,
            classContainer: "form-group col-md-12 div-pregunta ",
            classLabel: "radio-inline",
            classInput: "",
            variable,
            checked:false,
            labelsi:" SI",
            labelno:" NO" ,
            idSi,
            idNo
        },
    ]
}

export const submitButton = (label:String) =>{
    return [
        {
            type: formTypes.submit,
            label,
            classContainer: "form-group col-md-12",
            submitClass: "btn btn-primary btn-lg",
            align:"right",
        }
    ]
};
export const aTagButton = (label:String, href:String = "#", target:String = "_self") =>{
    return [
        {
            type: formTypes.click,
            label,
            href,
            target,
            classContainer: "form-group col-md-12",
            submitClass: "btn btn-secondary btn-md",
            align:"right",
        }
    ]
};

export const titleForm = (title:String) => {
    return [
        {
            type:  formTypes.title,
            label: title,
            classContainer: "form-group col-md-12",
            classLabel: "",
            align:"center"
            
        }
    ]
};

export const print = (label:String,variable:string,href:String = '#',target:String = '_self', required:boolean = false, multiple:boolean = true) => {
    return [
        {
            type:  formTypes.print,
            label,
            href,
            target,
            required,
            classContainer: "row col-md-12",
            classLabel: "glyphicon glyphicon-print",
            submitClass: "btn btn-primary btn-md",
            classInput: "input-lg",
            variable,
            multiple,
        },
    ]
}

export const divContainer = (variable:string,items:any,visible:boolean ) => {
    return [
        {
            type:  formTypes.divContainer,
            variable ,
            items,
            visible
        }
    ]
}

   