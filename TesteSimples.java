import br.com.warrick.biblioteca.controller.UsuarioController;
import br.com.warrick.biblioteca.utils.DatabaseManager;

public class TesteSimples {
    public static void main(String[] args) {
        System.out.println("Testando login...");

        DatabaseManager.init();

        UsuarioController controller = new UsuarioController();

        // Teste com usuário que deve existir no banco
        boolean resultado = controller.fazerLogin("usuario_teste", "senha123");

        if (resultado) {
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Login falhou!");
        }
    }
}
