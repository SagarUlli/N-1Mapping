package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Data;

@Entity
@Data
public class ObjectA {
	@Id
	private String name;
	private int followers;
	private String acc_type;

	@ManyToOne
	@Cascade(CascadeType.ALL)
	private ObjectB b;
}
