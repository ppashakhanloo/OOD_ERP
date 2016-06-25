package project;

import database.ModuleDAO;
import database.SystemDAO;

import java.util.ArrayList;
import java.util.Random;

public class System {
	int ID_LENGTH = 6;
	String ID;
	String name;

	public System() {
		this.setID(generateNDigitID(ID_LENGTH));
	}

	public System(String id, String name) {
		this.setID(id);
		this.setName(name);
	}

	private String generateNDigitID(int n) {
		Random random = new Random();
		return Integer.toString((int) (Math.pow(10, n) + random.nextFloat() * 9
				* Math.pow(10, n)));
	}

	public void removeModule(Module module) {
		ModuleDAO moduleDAO = ModuleDAO.getInstance();
		moduleDAO.remove(module.getID());
	}

	public int getModulesCount() {
		SystemDAO systemDAO = SystemDAO.getInstance();
		return systemDAO.getModules(getID()).size();
	}

	public ArrayList<Module> getModules() {
		SystemDAO systemDAO = SystemDAO.getInstance();
		return systemDAO.getModules(getID());
	}

	public Module getModule(String id) {
		ModuleDAO moduleDAO = ModuleDAO.getInstance();
		return moduleDAO.get(id);
	}

	public boolean addModule(Module module) {
		ModuleDAO moduleDAO = ModuleDAO.getInstance();
		return moduleDAO.add(module, getID());
	}

	public String getID() {
		return ID;
	}

	private void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public boolean setName(String name) {
		this.name = name;
		SystemDAO sysDAO = SystemDAO.getInstance();
		return sysDAO.update(new System(getID(), name));
	}

}
