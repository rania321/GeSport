package entities;

import java.util.Objects;

public class User {
    private int idU;
    private String NomU;
    private String PrenomU;
    private String EmailU;
    private String mdpU;
    private String RoleU;

    public User() {
    }

    public User(int idU, String nomU, String prenomU, String emailU, String mdpU, String roleU) {
        this.idU = idU;
        NomU = nomU;
        PrenomU = prenomU;
        EmailU = emailU;
        this.mdpU = mdpU;
        RoleU = roleU;
    }

    public User(String nomU, String prenomU, String emailU, String mdpU, String roleU) {
        NomU = nomU;
        PrenomU = prenomU;
        EmailU = emailU;
        this.mdpU = mdpU;
        RoleU = roleU;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getNomU() {
        return NomU;
    }

    public void setNomU(String nomU) {
        NomU = nomU;
    }

    public String getPrenomU() {
        return PrenomU;
    }

    public void setPrenomU(String prenomU) {
        PrenomU = prenomU;
    }

    public String getEmailU() {
        return EmailU;
    }

    public void setEmailU(String emailU) {
        EmailU = emailU;
    }

    public String getMdpU() {
        return mdpU;
    }

    public void setMdpU(String mdpU) {
        this.mdpU = mdpU;
    }

    public String getRoleU() {
        return RoleU;
    }

    public void setRoleU(String roleU) {
        RoleU = roleU;
    }

    @Override
    public String toString() {
        return "User{" +
                "idU=" + idU +
                ", NomU='" + NomU + '\'' +
                ", PrenomU='" + PrenomU + '\'' +
                ", EmailU='" + EmailU + '\'' +
                ", mdpU='" + mdpU + '\'' +
                ", RoleU='" + RoleU + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return idU == user.idU && Objects.equals(NomU, user.NomU) && Objects.equals(PrenomU, user.PrenomU) && Objects.equals(EmailU, user.EmailU) && Objects.equals(mdpU, user.mdpU) && Objects.equals(RoleU, user.RoleU);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idU, NomU, PrenomU, EmailU, mdpU, RoleU);
    }
}
