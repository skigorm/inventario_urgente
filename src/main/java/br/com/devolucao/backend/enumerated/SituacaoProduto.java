package br.com.devolucao.backend.enumerated;

public enum SituacaoProduto {
    ENCONTRADO(1),
    NAO_ENCONTRADO(2);

    private final int codigo;

    SituacaoProduto(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static SituacaoProduto porCodigo(int codigo) {
        for (SituacaoProduto situacao : values()) {
            if (situacao.codigo == codigo) {
                return situacao;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }

}
