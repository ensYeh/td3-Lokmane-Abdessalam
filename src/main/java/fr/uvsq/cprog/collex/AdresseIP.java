package fr.uvsq.cprog.collex;

public class AdresseIP {
    private final int pre_byte;
    private final int deux_byte;
    private final int tro_byte;
    private final int quatr_byte;

    public AdresseIP(int pre_byte, int deux_byte, int tro_byte, int quatr_byte) {
        this.pre_byte = pre_byte;
        this.deux_byte = deux_byte;
        this.tro_byte = tro_byte;
        this.quatr_byte = quatr_byte;
    }
}
