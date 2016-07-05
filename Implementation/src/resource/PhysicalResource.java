package resource;

public class PhysicalResource extends Resource {
    String name;
    String model;
    String location;

    public PhysicalResource(String name, String model, String location) {
        super();
        this.name = name;
        this.model = model;
        this.location = location;
    }

    public PhysicalResource(String id, String name, String model, String location) {
        super();
        setID(id);
        this.name = name;
        this.model = model;
        this.location = location;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
//        return super.toString() + ",\n" + "name=" + name + ", model=" + model
//                + ", location=" + location;
        return name + " (" + model + ") " + super.toString();
    }

}
