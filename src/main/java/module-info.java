module tfpoo.main {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;

    opens biblioteca.Lib to javafx.base;
    exports biblioteca;
    exports biblioteca.Lib;
}