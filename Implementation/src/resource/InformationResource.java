package resource;

public class InformationResource extends Resource {
    private String name;
    private String description;

    public InformationResource(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public InformationResource(String id, String name, String description) {
        super();
        setID(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
//        return super.toString() + ",\n" + "name=" + name + ", description=" + description;
        return name + " (" + description + ") " + super.toString();
    }

}
