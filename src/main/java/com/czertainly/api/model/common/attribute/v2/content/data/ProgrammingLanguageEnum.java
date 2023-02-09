package com.czertainly.api.model.common.attribute.v2.content.data;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum ProgrammingLanguageEnum {

    CSS ("css","language-css"),
    C_LIKE ("clike","language-clike"),
    JAVASCRIPT ("javascript","language-javascript"),
    ABAP ("abap","language-abap"),
    ACTIONSCRIPT ("actionscript","language-actionscript"),
    ADA ("ada","language-ada"),
    APACHE_CONFIGURATION ("apacheconf","language-apacheconf"),
    APL ("apl","language-apl"),
    APPLESCRIPT ("applescript","language-applescript"),
    ARDUINO ("arduino","language-arduino"),
    ARFF ("arff","language-arff"),
    ASCIIDOC ("asciidoc","language-asciidoc"),
    ASSEMBLY_6502 ("asm6502","language-asm6502"),
    ASP_NET ("aspnet","language-aspnet"),
    AUTOHOTKEY ("autohotkey","language-autohotkey"),
    AUTOIT ("autoit","language-autoit"),
    BASH ("bash","language-bash"),
    BASIC ("basic","language-basic"),
    BATCH ("batch","language-batch"),
    BISON ("bison","language-bison"),
    BRAINFUCK ("brainfuck","language-brainfuck"),
    BRO ("bro","language-bro"),
    C ("c","language-c"),
    C_SHARP ("csharp","language-csharp"),
    CPP ("cpp","language-cpp"),
    COFFEESCRIPT ("coffeescript","language-coffeescript"),
    CLOJURE ("clojure","language-clojure"),
    CRYSTAL ("crystal","language-crystal"),
    CONTENT_SECURITY_POLICY ("csp","language-csp"),
    CSS_EXTRAS ("css-extras","language-css-extras"),
    D ("d","language-d"),
    DART ("dart","language-dart"),
    DIFF ("diff","language-diff"),
    DJANGO_JINJA2 ("django","language-django"),
    DOCKER ("docker","language-docker"),
    EIFFEL ("eiffel","language-eiffel"),
    ELIXIR ("elixir","language-elixir"),
    ELM ("elm","language-elm"),
    ERB ("erb","language-erb"),
    ERLANG ("erlang","language-erlang"),
    F_SHARP ("fsharp","language-fsharp"),
    FLOW ("flow","language-flow"),
    FORTRAN ("fortran","language-fortran"),
    GEDCOM ("gedcom","language-gedcom"),
    GHERKIN ("gherkin","language-gherkin"),
    GIT ("git","language-git"),
    GLSL ("glsl","language-glsl"),
    GAMEMAKER_LANGUAGE ("gml","language-gml"),
    GO ("go","language-go"),
    GRAPHQL ("graphql","language-graphql"),
    GROOVY ("groovy","language-groovy"),
    HAML ("haml","language-haml"),
    HANDLEBARS ("handlebars","language-handlebars"),
    HASKELL ("haskell","language-haskell"),
    HAXE ("haxe","language-haxe"),
    HTTP ("http","language-http"),
    HTTP_PUBLIC_KEY_PINS ("hpkp","language-hpkp"),
    HTTP_STRICT_TRANSPORT_SECURITY ("hsts","language-hsts"),
    ICHIGOJAM ("ichigojam","language-ichigojam"),
    ICON ("icon","language-icon"),
    INFORM_7 ("inform7","language-inform7"),
    INI ("ini","language-ini"),
    IO ("io","language-io"),
    J ("j","language-j"),
    JAVA ("java","language-java"),
    JOLIE ("jolie","language-jolie"),
    JSON ("json","language-json"),
    JULIA ("julia","language-julia"),
    KEYMAN ("keyman","language-keyman"),
    KOTLIN ("kotlin","language-kotlin"),
    LATEX ("latex","language-latex"),
    LESS ("less","language-less"),
    LIQUID ("liquid","language-liquid"),
    LISP ("lisp","language-lisp"),
    LIVESCRIPT ("livescript","language-livescript"),
    LOLCODE ("lolcode","language-lolcode"),
    LUA ("lua","language-lua"),
    MAKEFILE ("makefile","language-makefile"),
    MARKDOWN ("markdown","language-markdown"),
    MARKUP_TEMPLATING ("markup-templating","language-markup-templating"),
    MATLAB ("matlab","language-matlab"),
    MEL ("mel","language-mel"),
    MIZAR ("mizar","language-mizar"),
    MONKEY ("monkey","language-monkey"),
    N4JS ("n4js","language-n4js"),
    NASM ("nasm","language-nasm"),
    NGINX ("nginx","language-nginx"),
    NIM ("nim","language-nim"),
    NIX ("nix","language-nix"),
    NSIS ("nsis","language-nsis"),
    OBJECTIVE_C ("objectivec","language-objectivec"),
    OCAML ("ocaml","language-ocaml"),
    OPENCL ("opencl","language-opencl"),
    OZ ("oz","language-oz"),
    PARI_GP ("parigp","language-parigp"),
    PARSER ("parser","language-parser"),
    PASCAL ("pascal","language-pascal"),
    PERL ("perl","language-perl"),
    PHP ("php","language-php"),
    PHP_EXTRAS ("php-extras","language-php-extras"),
    PL_SQL ("plsql","language-plsql"),
    POWERSHELL ("powershell","language-powershell"),
    PROCESSING ("processing","language-processing"),
    PROLOG ("prolog","language-prolog"),
    PROPERTIES ("properties","language-properties"),
    PROTOCOL_BUFFERS ("protobuf","language-protobuf"),
    PUG ("pug","language-pug"),
    PUPPET ("puppet","language-puppet"),
    PURE ("pure","language-pure"),
    PYTHON ("python","language-python"),
    Q ("q","language-q"),
    QORE ("qore","language-qore"),
    R ("r","language-r"),
    REACT_JSX ("jsx","language-jsx"),
    REACT_TSX ("tsx","language-tsx"),
    REN_PY ("renpy","language-renpy"),
    REASON ("reason","language-reason"),
    REST ("rest","language-rest"),
    RIP ("rip","language-rip"),
    ROBOCONF ("roboconf","language-roboconf"),
    RUBY ("ruby","language-ruby"),
    RUST ("rust","language-rust"),
    SAS ("sas","language-sas"),
    SASS_SASS ("sass","language-sass"),
    SASS_SCSS ("scss","language-scss"),
    SCALA ("scala","language-scala"),
    SCHEME ("scheme","language-scheme"),
    SMALLTALK ("smalltalk","language-smalltalk"),
    SMARTY ("smarty","language-smarty"),
    SQL ("sql","language-sql"),
    SOY ("soy","language-soy"),
    STYLUS ("stylus","language-stylus"),
    SWIFT ("swift","language-swift"),
    TAP ("tap","language-tap"),
    TCL ("tcl","language-tcl"),
    TEXTILE ("textile","language-textile"),
    TEMPLATE_TOOLKIT_2 ("tt2","language-tt2"),
    TWIG ("twig","language-twig"),
    TYPESCRIPT ("typescript","language-typescript"),
    VB_NET ("vbnet","language-vbnet"),
    VELOCITY ("velocity","language-velocity"),
    VERILOG ("verilog","language-verilog"),
    VHDL ("vhdl","language-vhdl"),
    VIM ("vim","language-vim"),
    VISUAL_BASIC ("visual-basic","language-visual-basic"),
    WEBASSEMBLY ("wasm","language-wasm"),
    WIKI_MARKUP ("wiki","language-wiki"),
    XEORA ("xeora","language-xeora"),
    XOJO ("xojo","language-xojo"),
    XQUERY ("xquery","language-xquery"),
    YAML ("yaml","language-yaml");


    private String code;

    private String markupClass;

    ProgrammingLanguageEnum(final String code, final String markupClass) {
        this.code = code;
        this.markupClass = markupClass;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static ProgrammingLanguageEnum findByCode(String code) {
        return Arrays.stream(ProgrammingLanguageEnum.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown programming language {}", code)));
    }
}
