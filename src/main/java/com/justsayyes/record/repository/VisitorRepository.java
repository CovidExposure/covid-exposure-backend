package com.justsayyes.record.repository;

import com.justsayyes.record.Entity.Record;
import com.justsayyes.record.Entity.Visitor;
import org.springframework.data.repository.CrudRepository;

public interface VisitorRepository extends CrudRepository<Visitor, String> {
}
