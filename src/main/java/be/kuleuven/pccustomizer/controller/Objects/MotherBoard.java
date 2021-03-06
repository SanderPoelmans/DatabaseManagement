package be.kuleuven.pccustomizer.controller.Objects;

public class MotherBoard {
    private String name;
    private boolean hasWifi;
    private int price;
    private String caseSize;
    private int RAMSlots;
    private int PCIESlots;
    private int aantal;

    public MotherBoard(String name, boolean hasWifi, int price, String caseSize, int RAMSlots, int PCIESlots, int aantal) {
        this.name = name;
        this.hasWifi = hasWifi;
        this.price = price;
        this.caseSize = caseSize;
        this.RAMSlots = RAMSlots;
        this.PCIESlots = PCIESlots;
        this.aantal = aantal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCaseSize() {
        return caseSize;
    }

    public void setCaseSize(String caseSize) {
        this.caseSize = caseSize;
    }

    public int getRAMSlots() {
        return RAMSlots;
    }

    public void setRAMSlots(int RAMSlots) {
        this.RAMSlots = RAMSlots;
    }

    public int getPCIESlots() {
        return PCIESlots;
    }

    public void setPCIESlots(int PCIESlots) {
        this.PCIESlots = PCIESlots;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
}
