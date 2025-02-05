package br.com.devolucao.backend.enumerated;

public enum SituacaoChamado {
	
    PENDENTE(1),
    EM_ANDAMENTO(2),
    FINALIZADO(3);

    private final int codigo;

    SituacaoChamado(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static SituacaoChamado porCodigo(int codigo) {
        for (SituacaoChamado situacao : values()) {
            if (situacao.codigo == codigo) {
                return situacao;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }

}
