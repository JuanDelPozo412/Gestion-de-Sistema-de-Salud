package repository;

interface iValidaciones {

    public static boolean esMail(String mail) {
        if(mail.matches("^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$")) {
            return true;
        }
        return false;
    }

    public static boolean esContrasena(String contrasena) {
        // valida que la contraseña tenga minimo 8 y un maximo de 40 caracteres
        // debe tener minimo 1 mayuscula y tres numeros
        if(contrasena.matches("^(?=.*[A-Z])(?=(?:.*\\d){3,}).{8,40}$\n")) {
            return true;
        }
        return false;
    }

    public static boolean esEntero(String entero) {
        try {
            int stringParseado = Integer.parseInt(entero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}