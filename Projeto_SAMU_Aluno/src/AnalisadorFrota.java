/**
 * Ferramenta de análise que responde à pergunta central do problema:
 * qual é a frota (ambulâncias básicas + UTIs móveis) de MENOR CUSTO MENSAL
 * capaz de atingir todas as metas de desempenho definidas em Estatisticas?
 *
 * Executa simulações rápidas (sem interface gráfica) para cada combinação
 * de frota. Como Simulacao reinicia o Randomizador, todas as combinações são
 * avaliadas sob a mesma sequência de eventos aleatórios (comparação justa).
 */
public class AnalisadorFrota
{
    public static final int MAX_BASICAS = 4;
    public static final int MAX_UTIS = 2;
    /** Número de etapas de cada simulação de análise (longa, para reduzir a variação). */
    public static final int ETAPAS_ANALISE = 2000;
    /** Custo mensal de operação, em milhares de reais. */
    public static final int CUSTO_MENSAL_BASICA = 40;
    public static final int CUSTO_MENSAL_UTI = 100;

    private final int etapas;

    public AnalisadorFrota()
    {
        this(ETAPAS_ANALISE);
    }

    public AnalisadorFrota(int etapas)
    {
        if(etapas <= 0) {
            throw new IllegalArgumentException("Número de etapas deve ser positivo.");
        }
        this.etapas = etapas;
    }

    /**
     * Executa uma simulação sem tela e devolve suas estatísticas.
     */
    public Estatisticas avalia(int qtdBasicas, int qtdUtis)
    {
        Simulacao simulacao = new Simulacao(qtdBasicas, qtdUtis, false);
        simulacao.simula(etapas);
        return simulacao.getEstatisticas();
    }

    /**
     * @return O custo mensal de uma frota, em milhares de reais.
     */
    public static int custoMensal(int qtdBasicas, int qtdUtis)
    {
        return qtdBasicas * CUSTO_MENSAL_BASICA + qtdUtis * CUSTO_MENSAL_UTI;
    }

    /**
     * Procura, entre todas as combinações até MAX_BASICAS e MAX_UTIS, a frota
     * de menor custo mensal que atinge todas as metas. Em caso de empate no
     * custo, prefere a frota com menos veículos.
     * @return {qtdBasicas, qtdUtis} ou null se nenhuma combinação atingir as metas.
     */
    public int[] encontraFrotaMinima()
{
    int[] melhor = null;
    int menorCusto = Integer.MAX_VALUE;

    for(int utis = 0; utis <= MAX_UTIS; utis++) {
        for(int basicas = 0; basicas <= MAX_BASICAS; basicas++) {
            if(avalia(basicas, utis).metasAtingidas()) {
                int custo = custoMensal(basicas, utis);
                int totalVeiculos = basicas + utis;

                if(custo < menorCusto) {
                    melhor = new int[] {basicas, utis};
                    menorCusto = custo;
                }
                else if(custo == menorCusto && melhor != null
                        && totalVeiculos < melhor[0] + melhor[1]) {
                    melhor = new int[] {basicas, utis};
                }
            }
        }
    }
    return melhor;
}

    /**
     * Imprime no terminal uma tabela com os indicadores de várias frotas.
     */
    public void imprimeTabela()
    {
        System.out.println("Básicas UTIs Custo | Resp.média Resp.grave Perda(%) | Metas");
        for(int utis = 1; utis <= MAX_UTIS; utis++) {
            for(int basicas = 1; basicas <= MAX_BASICAS; basicas++) {
                Estatisticas e = avalia(basicas, utis);
                System.out.printf("%7d %4d %5d | %10.1f %10.1f %8.1f | %s%n", basicas, utis,
                                  custoMensal(basicas, utis), e.getTempoMedioResposta(),
                                  e.getTempoMedioResposta(Gravidade.GRAVE), e.getTaxaPerda() * 100,
                                  e.metasAtingidas() ? "sim" : "não");
            }
        }
    }

   public static void main(String[] args)
    {
        AnalisadorFrota analisador = new AnalisadorFrota();
        analisador.imprimeTabela();
        int[] frota = analisador.encontraFrotaMinima();
        if(frota == null) {
            System.out.println("Nenhuma frota testada atinge as metas.");
        }
        else {
            System.out.println("Frota de menor custo que atinge as metas: " + frota[0]
                               + " ambulância(s) básica(s) e " + frota[1] + " UTI(s) móvel(is) - R$ "
                               + custoMensal(frota[0], frota[1]) + " mil/mês.");
        }
    }
}
