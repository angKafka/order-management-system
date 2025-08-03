package org.rdutta.commonlibrary.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class OrderValidationStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean inventoryValid;
    private boolean userValid;
    private boolean inventoryFailed;
    private boolean userFailed;

    public void setInventoryValid(boolean isValid) {
        inventoryValid = isValid;
        inventoryFailed = !isValid;
    }

    public void setUserValid(boolean isValid) {
        userValid = isValid;
        userFailed = !isValid;
    }

    public boolean isAllValid() {
        return inventoryValid && userValid;
    }

    public boolean hasFailure() {
        return inventoryFailed || userFailed;
    }
}