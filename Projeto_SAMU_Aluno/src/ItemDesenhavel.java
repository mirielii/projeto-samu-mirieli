import java.awt.Color;

/**
 * Interface para todos os elementos que ocupam uma posição na cidade
 * e podem ser desenhados na interface gráfica.
 */
public interface ItemDesenhavel
{
    /**
     * @return A localização atual do item na cidade.
     */
    Localizacao getLocalizacao()
    {
        return null;
    }

    /**
     * @return A cor usada para desenhar o item na tela.
     */
    Color getCor();
}
