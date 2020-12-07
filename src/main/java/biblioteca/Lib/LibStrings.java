package biblioteca.Lib;

public enum LibStrings {
    SAVED_LOCATION("src/biblioteca.Saved/save.txt"),
    EQUIPE_LOCATION("src/biblioteca.Saved/equipe.txt"),
    ITEM_LIMIT("FIM_SAVE"),
    CLIENT_LIMIT("ITEMS"),
    LIVRO("LIVRO"),
    REVISTA("REVISTA"),
    JORNAL("JORNAL"),
    MAX_LIVROS_EMPRESTADOS("8"),
    MAX_LIVROS_RESERVADOS("6");

    final String value;
    LibStrings(String s) {
        value = s;
    }
    public String get() {
        return value;
    }
}
