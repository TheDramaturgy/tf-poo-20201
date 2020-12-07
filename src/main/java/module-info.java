module  tfpoo {
    requires javafx.graphics;
    requires javafx.controls;

    opens biblioteca.Lib to javafx.base;
    exports biblioteca;
}