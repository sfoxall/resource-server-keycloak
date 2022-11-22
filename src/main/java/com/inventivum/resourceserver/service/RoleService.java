package com.inventivum.resourceserver.service;

import java.util.List;

public interface RoleService {

    List<String> getRoles(String email);

    boolean hasPermission(String permissionName, String pageName, String supplierId);

}
