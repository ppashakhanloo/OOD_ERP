package resource;

import database.ResourceDAO;
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
        this.resourceStatus = resourceStatus;
        this.isAvailable = isAvailable;
        setID(iD);
    }

    public ResourceStatus getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(ResourceStatus resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public boolean setProjectID(String pid) {
        ResourceDAO dao = ResourceDAO.getInstance();
        return dao.setProjectID(getID(), pid);
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
//        return "وضعیت" + ": " + resourceStatus + " (" + getID() + ")";
    }
}
