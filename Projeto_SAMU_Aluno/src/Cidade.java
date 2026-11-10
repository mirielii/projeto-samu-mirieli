import java.util.ArrayList;
import java.util.List;

/**
 * Representa a cidade onde ocorre a simulação: uma grade retangular de
 * largura x altura células e a coleção de itens nela presentes
 * (hospitais, pacientes aguardando socorro e veículos).
 */
public class Cidade
{
    public static final int LARGURA_PADRAO = 30;
    public static final int ALTURA_PADRAO = 30;

    private int largura;
    private int altura;
    private List<Object> itens;

    public Cidade()
    {
        this(LARGURA_PADRAO, ALTURA_PADRAO);
    }

    public Cidade(int largura, int altura)
    {
        this.largura = largura;
        this.altura = altura;
        itens = new ArrayList<Object>();
    }

    /**
     * Adiciona um item à cidade (itens repetidos são ignorados).
     */
    public void adicionaItem(Object item)
    {
        if(!itens.contains(item)) {
            itens.add(item);
        }
    }

    public void removeItem(Object item)
    {
        itens.remove(item);
    }

    public boolean contem(Object item)
    {
        return itens.contains(item);
    }

    public List<Object> getItens()
    {
        return itens;
    }

    /**
     * @return true se a localização está dentro dos limites da cidade.
     */
    public boolean estaDentro(Localizacao localizacao)
    {
        return localizacao.getx()>=0
            && localizacao.getx()<largura
            && localizacao.gety()>=0
            && localizacao.gety()<altura;
    }

    public int getLargura()
    {
        return largura;
    }

    public int getAltura()
    {
        return altura;
    }
}
