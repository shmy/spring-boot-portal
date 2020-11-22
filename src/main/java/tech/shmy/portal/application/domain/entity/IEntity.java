package tech.shmy.portal.application.domain.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class IEntity<ID extends Serializable> {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;
    private Date createdAt;
    private Date updatedAt;
}
