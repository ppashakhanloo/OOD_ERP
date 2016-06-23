package resource;

public class InformationResource extends Resource {
	String name;
	String description;

	public InformationResource(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return super.toString() + ",\n" + "name=" + name + ", description=" + description;
	}

}
