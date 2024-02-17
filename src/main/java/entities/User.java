package entities;

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
        this.NomU = nomU;
        this.PrenomU = prenomU;
        this.EmailU = emailU;
        this.mdpU = mdpU;
        this.RoleU = roleU;
    }

    public User(String nomU, String prenomU, String emailU, String mdpU, String roleU) {
        this.NomU = nomU;
        this.PrenomU = prenomU;
        this.EmailU = emailU;
        this.mdpU = mdpU;
        this.RoleU = roleU;
    }

    public int getIdU() {
        return this.idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getNomU() {
        return this.NomU;
    }

    public void setNomU(String nomU) {
        this.NomU = nomU;
    }

    public String getPrenomU() {
        return this.PrenomU;
    }

    public void setPrenomU(String prenomU) {
        this.PrenomU = prenomU;
    }

    public String getEmailU() {
        return this.EmailU;
    }

    public void setEmailU(String emailU) {
        this.EmailU = emailU;
    }

    public String getMdpU() {
        return this.mdpU;
    }

    public void setMdpU(String mdpU) {
        this.mdpU = mdpU;
    }

    public String getRoleU() {
        return this.RoleU;
    }

    public void setRoleU(String roleU) {
        this.RoleU = roleU;
    }

    public String toString() {
        return "User{idU=" + this.idU + ", NomU='" + this.NomU + "', PrenomU='" + this.PrenomU + "', EmailU='" + this.EmailU + "', mdpU='" + this.mdpU + "', RoleU='" + this.RoleU + "'}";
    }
}
