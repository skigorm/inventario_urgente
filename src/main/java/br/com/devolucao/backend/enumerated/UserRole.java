package br.com.devolucao.backend.enumerated;

/**
 * Enumeração dos papéis de usuário no sistema.
 */
public enum UserRole {

    LOJA_PROPRIA("loja_propria"),
    AGENTE_AUTORIZADO("agente_autorizado"),;

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    /**
     * Obtém o papel como string.
     * 
     * @return o papel do usuário.
     */
    public String getRole() {
        return role;
    }
}
