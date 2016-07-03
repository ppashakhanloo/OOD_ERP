package resource;

import utility.Identifiable;

public class Resource extends Identifiable {
    private ResourceStatus resourceStatus;
    private boolean isAvailable;

    public Resource() {
        this.setID(generateNDigitID(ID_LENGTH));
        this.setResourceStatus(ResourceStatus.IDLE);
        this.setAvailable(true);
    }

    public Resource(String iD, ResourceStatus resourceStatus, boolean isAvailable) {
        super();
        this.resourceStatus = resourceStatus;
        this.isAvailable = isAvailable;
        setID(iD);
    }

    public void edit(Resource resource) {
        this.resourceStatus = resource.getResourceStatus();
        this.isAvailable = resource.isAvailable();
    }

    public ResourceStatus getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(ResourceStatus resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "ID=" + getID() + ", resourceStatus=" + resourceStatus + ", isAvailable=" + isAvailable;
    }
}
