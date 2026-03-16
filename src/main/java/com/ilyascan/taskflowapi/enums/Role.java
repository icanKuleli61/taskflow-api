package com.ilyascan.taskflowapi.enums;

public enum Role {

    OWNER("Owner","Pano sahibi, tüm haklara sahip",100),
    ADMIN("Admin","Pano yöneticisi, üye ekleyebilir ve ayarları değiştirebilir",80),
    MEMBER("Member","Standart üye kart oluşturabilir ve düzenleyebilir.",50),
    OBSERVER("Observer","Gözlemci, sadece görüntüleme yapabilir.",10);

    private String roleName;

    private String roleDesc;

    private int roleValue;

    Role(String roleName, String roleDesc, int roleValue) {
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.roleValue = roleValue;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public int getRoleValue() {
        return roleValue;
    }
}
