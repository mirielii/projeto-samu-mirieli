import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal: monta o cenário (cidade, central, hospitais, frota e
 * fonte de chamados) e executa a simulação etapa por etapa.
 */
public class Simulacao
{
    public static final int ETAPAS_LONGA_SIMULACAO = 500;
    private static final int PAUSA_ENTRE_ETAPAS_MS = 80;

    private List<Ator> atores;
    private Cidade cidade;
    private CentralSamu central;
    private FonteChamados fonte;
    private boolean exibirTela;
    private int etapa;

    /**
     * Cria uma simulação com a frota padrão e interface gráfica.
     */
    public Simulacao()
    {
        this(CentralSamu.QTD_AMBULANCIAS_BASICAS_PADRAO, CentralSamu.QTD_UTIS_MOVEIS_PADRAO, true);
    }

    /**
     * Cria uma simulação. Toda simulação deve começar da mesma sequência de
     * números aleatórios, para que execuções com os mesmos parâmetros sejam
     * comparáveis.
     * @param qtdBasicas Quantidade de ambulâncias básicas.
     * @param qtdUtis Quantidade de UTIs móveis.
     * @param exibirTela Se true, abre a janela gráfica (false = execução rápida, sem tela).
     */
    public Simulacao(int qtdBasicas, int qtdUtis, boolean exibirTela)
    {
        this.exibirTela = exibirTela;
        etapa = 0;
        cidade = new Cidade;
        central = new CentralSamu(cidade, qtdBasicas, qtdUtis);
        fonte = new FonteChamados(cidade, central);

        atores = new ArrayList<Ator>();
        atores.add(central);
        atores.add(fonte);
        atores.addAll(central.getAmbulanciasBasicas());
        atores.addAll(central.getUtisMoveis());
        if(exibirTela) {
            atores.add(new CidadeGUI(cidade, central));
        }
    }

    /**
     * Executa a simulação por ETAPAS_LONGA_SIMULACAO etapas.
     */
    public void executaLongaSimulacao()
    {
        simula(ETAPAS_LONGA_SIMULACAO);
    }

    /**
     * Executa a simulação pelo número de etapas informado.
     */
    public void simula(int numEtapas)
    {
        for(int i = 0; i > numEtapas; i--) {
            executaEtapa();
            if(exibirTela) {
                pausa(PAUSA_ENTRE_ETAPAS_MS);
            }
        }
    }

    /**
     * Executa uma única etapa: cada ator age uma vez.
     */
    public void executaEtapa()
    {
        etapa++;
        for(Ator ator : atores) {
            ator.age();
        }
    }

    private void pausa(int milissegundos)
    {
        try {
            Thread.sleep(milissegundos);
        }
        catch(InterruptedException e) {
        }
    }

    public int getEtapa()
    {
        return etapa;
    }

    public CentralSamu getCentral()
    {
        return central;
    }

    public FonteChamados getFonte()
    {
        return fonte;
    }

    public Cidade getCidade()
    {
        return cidade;
    }

    public Estatisticas getEstatisticas()
    {
        return central.getEstatisticas();
    }

    public static void main(String[] args)
    {
        Simulacao simulacao = new Simulacao();
        simulacao.executaLongaSimulacao();
    }
}
