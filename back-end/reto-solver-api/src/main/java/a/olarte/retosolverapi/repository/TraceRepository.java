package a.olarte.retosolverapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import a.olarte.retosolverapi.model.TraceModel;

@Repository
public interface TraceRepository extends JpaRepository<TraceModel, Integer>{

}
