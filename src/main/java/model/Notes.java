package model;
import javax.persistence.*;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "Notes")
public class Notes {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tittle;
	private String description;

    // Automatically updated on every update
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
      	this.updatedAt = LocalDateTime.now();
    }

    // Automatically called before UPDATE
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
	
	public Notes() {}
	
	public Notes(String Tittle,String Description ){
		this.tittle=Tittle;
		this.description=Description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDateTime getUpdatedAt() {
	    return updatedAt;
	}

}
