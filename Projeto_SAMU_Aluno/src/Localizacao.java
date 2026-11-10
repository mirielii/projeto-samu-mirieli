/**
 * Representa uma posição (x, y) na grade bidimensional da cidade.
 */
public class Localizacao
{
    private int x;
    private int y;

    /**
     * @param x Coordenada horizontal (coluna), deve ser >= 0.
     * @param y Coordenada vertical (linha), deve ser >= 0.
     */
    public Localizacao(int x, int y)
    {
        if(x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordenadas negativas: " + x + "," + y);
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Calcula a próxima localização a ser visitada para se aproximar do destino.
     * O deslocamento é de no máximo uma célula em cada eixo (permite diagonais).
     * @param destino A localização de destino.
     * @return A localização vizinha mais próxima do destino (ou o próprio destino).
     */
    public Localizacao proximaLocalizacao(Localizacao destino)
    {
        if(destino.equals(this)) {
            return destino;
        }
        int deslocX = Integer.compare(destino.getX(), x);
        int deslocY = Integer.compare(destino.getY(), y);
        return new Localizacao(x + deslocX, y + deslocY);
    }

    /**
     * Calcula a distância, em etapas de deslocamento, até o destino.
     * Como o movimento diagonal é permitido, a distância é o maior dos
     * deslocamentos em x e em y.
     * @param destino A localização de destino.
     * @return O número de etapas necessárias para chegar ao destino.
     */
    public int distancia(Localizacao destino)
    {
        int distX = Math.abs(destino.getX() - x);
        int distY = Math.abs(destino.getY() - y);
        return Math.max(distX, distY);
    }

    public boolean equals(Object outro)
    {
        if(outro instanceof Localizacao) {
            Localizacao outra = (Localizacao) outro;
            return x == outra.getX() && y == outra.getY();
        }
        return false;
    }

    public int hashCode()
    {
        return (y << 16) + x;
    }

    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
