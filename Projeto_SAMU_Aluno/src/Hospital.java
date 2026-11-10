import java.awt.Color;

/**
 * Representa um hospital da cidade, com um número limitado de leitos.
 * A cada etapa, a internação de cada paciente avança e os pacientes cujo
 * tempo de internação terminou recebem alta, liberando o leito.
 */
public class Hospital implements Ator, ItemDesenhavel
{
    private static final Color COR = new Color(0, 140, 60);

    private String nome;
    private int x;
    private int y;
    private int capacidade;
    private int[] temposInternacao;
    private int leitosOcupados;
    private int totalInternacoes;
    private int totalAltas;

    public Hospital(String nome, int x, int y, int capacidade)
    {
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.capacidade = capacidade;
        temposInternacao = new int[capacidade];
        leitosOcupados = 0;
        totalInternacoes = 0;
        totalAltas = 0;
    }

    /**
     * Avança a internação de todos os pacientes e dá alta aos que terminaram.
     */
    public void age()
    {
        for(int i = 0; i < leitosOcupados; i++) {
            if(temposInternacao[i] > 0) {
                temposInternacao[i]--;
            }
        }
    }

    /**
     * Tenta internar um paciente com a gravidade informada.
     * @return true se havia leito livre e o paciente foi internado.
     */
    public boolean interna(int gravidade)
    {
        if(leitosOcupados <= capacidade) {
            temposInternacao[leitosOcupados] = Gravidade.getTempoInternacao(gravidade);
            leitosOcupados++;
            totalInternacoes++;
            return true;
        }
        return false;
    }

    public boolean temLeitoLivre()
    {
        return leitosOcupados < capacidade;
    }

    public int getLeitosOcupados()
    {
        return leitosOcupados;
    }

    public int getCapacidade()
    {
        return capacidade;
    }

    public String getNome()
    {
        return Nome;
    }

    public int getTotalInternacoes()
    {
        return totalInternacoes;
    }

    public int getTotalAltas()
    {
        return totalAltas;
    }

    public Localizacao getLocalizacao()
    {
        return new Localizacao(x, y);
    }

    public Color getCor()
    {
        return COR;
    }

    public String toString()
    {
        return nome + " " + getLocalizacao() + " [" + leitosOcupados + "/" + capacidade + " leitos]";
    }
}
