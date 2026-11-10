import java.awt.Color;

/**
 * Níveis de gravidade de um chamado de emergência (classificação de risco).
 * Quanto maior o valor, maior a prioridade do chamado.
 */
public class Gravidade
{
    public static final int LEVE = 1;
    public static final int MODERADO = 2;
    public static final int GRAVE = 3;

    public static String getNome(int gravidade)
    {
        switch(gravidade) {
            case LEVE:
                return "Leve";
            case MODERADO:
                return "Moderado";
            case GRAVE:
                return "Grave";
            default:
                throw new IllegalArgumentException("Gravidade inválida: " + gravidade);
        }
    }

    /**
     * @return Número de etapas que um paciente deste nível fica internado.
     */
    public static int getTempoInternacao(int gravidade)
    {
        if(gravidade == LEVE) {
            return 15;
        }
        if(gravidade == MODERADO) {
            return 40;
        }
        if(gravidade == GRAVE) {
            return 80;
        }
        throw new IllegalArgumentException("Gravidade inválida: " + gravidade);
    }

    public static Color getCor(int gravidade)
    {
        if(gravidade == LEVE) {
            return new Color(230, 190, 0);
        }
        if(gravidade == MODERADO) {
            return new Color(255, 120, 0);
        }
        if(gravidade == GRAVE) {
            return new Color(210, 0, 0);
        }
        throw new IllegalArgumentException("Gravidade inválida: " + gravidade);
    }

    /**
     * @return true se este nível exige atendimento por UTI Móvel.
     */
    public static boolean ehCritica(int gravidade)
    {
        if(gravidade != LEVE && gravidade != MODERADO && gravidade != GRAVE) {
            throw new IllegalArgumentException("Gravidade inválida: " + gravidade);
        }

        return gravidade == GRAVE;
    }
}