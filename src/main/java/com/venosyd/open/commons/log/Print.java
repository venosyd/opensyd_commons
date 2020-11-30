package com.venosyd.open.commons.log;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Essa classe existe para evitar que Heisenbug nos ataque. Heisenbug eh
 *         um bug do debug que nos impede de ver um bug, porque ao observar um
 *         suposto bug ele desaparece com o bug.
 * 
 *         Nao use essa classe, obviamente ela eh abstrata, entao use as
 *         concretas Out ou Err que dizem a superclasse qual deve ser o
 *         printstream a ser usado para imprimir as mensagens
 * 
 *         Para facilitar a sua vida, implemente (implements) Debuggable na
 *         classe que voce quer averiguar, os objetos ja estao implementados
 *         nessa interface.
 */
public abstract class Print {

    /**
     * tamanho padrao da linha de tracos --------
     */
    private static final int DASHLINE_LENGTH = 80;
    private PrintStream _print;

    Print(PrintStream print) {
        _print = print;
    }

    /**
     * prints 40 default dashes
     */
    public Print dashes() {
        return dashes(DASHLINE_LENGTH);
    }

    /**
     * prints a number of default dashes
     */
    public Print dashes(int number) {
        return dashes(number, '-');
    }

    /**
     * prints a number of dashes
     */
    public Print dashes(int number, char dash) {
        return dashes(number, dash, true);
    }

    /**
     * prints a number of dashes with ou without jump
     */
    public Print dashes(int number, char dash, boolean jump) {
        for (int i = 0; i < number; i++) {
            _print.print(dash);
        }

        return jump ? br() : this;
    }

    /**
     * prints a object inside dashes
     */
    public Print inside(Object o) {
        return inside(o, '-');
    }

    /**
     * prints a object inside at custom dashes
     */
    public Print inside(Object o, char dash) {
        var printable = o.toString();
        int chunk = DASHLINE_LENGTH - printable.length();
        int dashes_num = ((chunk > 0 ? chunk : printable.length()) / 2) - 1;
        return dashes(dashes_num, dash, false).ln(' ', false).ln(o, false).ln(' ', false).dashes(dashes_num, dash,
                true);
    }

    /**
     * prints a exception
     */
    public Print exception(String tag, Exception e) {
        return ln().tag(tag).tag(e.toString()).ln()
                .list_(Arrays.asList(e.getStackTrace() == null ? new String[] {} : e.getStackTrace()));
    }

    /**
     * go to the next line
     */
    public Print br() {
        _print.print('\n');
        return this;
    }

    /**
     * prints a empty line
     */
    public Print ln() {
        _print.println();
        return this;
    }

    /**
     * prints a tag before the message, util for logging
     */
    public Print tag(String tag) {
        if (!tag.startsWith("["))
            tag = "[" + tag;
        if (!tag.endsWith("]"))
            tag = tag + "]";
        return ln(tag + " ", false);
    }

    /**
     * prints a line
     */
    public Print ln(Object obj) {
        return _collection(Arrays.asList(obj), false, false, false);
    }

    /**
     * prints a line without a jump
     */
    public Print ln(Object obj, boolean jump) {
        return _inline(Arrays.asList(obj), false, false, jump);
    }

    /**
     * prints a line beggining with dashes
     */
    public Print _ln(Object obj) {
        return _collection(Arrays.asList(obj), true, false, false);
    }

    /**
     * prints a line beggining with dashes
     */
    public Print _ln(Object obj, boolean jump) {
        return _collection(Arrays.asList(obj), true, false, jump);
    }

    /**
     * prints a line inside dashes
     */
    public Print _ln_(Object obj) {
        return _collection(Arrays.asList(obj), true, true, false);
    }

    /**
     * prints a line ending with dashes
     */
    public Print ln_(Object obj) {
        return _collection(Arrays.asList(obj), false, true, false);
    }

    /**
     * prints a line ending with dashes
     */
    public Print ln_(Object obj, boolean jump) {
        return _collection(Arrays.asList(obj), false, true, jump);
    }

    /**
     * prints a map
     */
    public Print map(Map<?, ?> map) {
        return _dictionary(map, false, false, false);
    }

    /**
     * prints a map without a jump
     */
    public Print map(Map<?, ?> map, boolean jump) {
        return _dictionary(map, false, false, jump);
    }

    /**
     * prints a map beggining with dashes
     */
    public Print _map(Map<?, ?> map) {
        return _dictionary(map, true, false, false);
    }

    /**
     * prints a map beggining with dashes
     */
    public Print _map(Map<?, ?> map, boolean jump) {
        return _dictionary(map, true, false, jump);
    }

    /**
     * prints a map inside dashes
     */
    public Print _map_(Map<?, ?> map) {
        return _dictionary(map, true, true, false);
    }

    /**
     * prints a map ending with dashes
     */
    public Print map_(Map<?, ?> map) {
        return _dictionary(map, false, true, false);
    }

    /**
     * prints a map ending with dashes
     */
    public Print map_(Map<?, ?> map, boolean jump) {
        return _dictionary(map, false, true, jump);
    }

    /**
     * prints a list
     */
    public Print list(Iterable<?> list) {
        return _collection(list, false, false, false);
    }

    /**
     * prints a list without a jump
     */
    public Print list(Iterable<?> list, boolean jump) {
        return _collection(list, false, false, jump);
    }

    /**
     * prints a list beggining with dashes
     */
    public Print _list(Iterable<?> list) {
        return _collection(list, true, false, false);
    }

    /**
     * prints a list beggining with dashes
     */
    public Print _list(Iterable<?> list, boolean jump) {
        return _collection(list, true, false, jump);
    }

    /**
     * prints a list inside dashes
     */
    public Print _list_(Iterable<?> list) {
        return _collection(list, true, true, false);
    }

    /**
     * prints a list ending with dashes
     */
    public Print list_(Iterable<?> list) {
        return _collection(list, false, true, false);
    }

    /**
     * prints a list ending with dashes
     */
    public Print list_(Iterable<?> list, boolean jump) {
        return _collection(list, false, true, jump);
    }

    /**
     * prints a inline collection
     */
    public Print inline(Iterable<?> inline) {
        return _inline(inline, false, false, true);
    }

    /**
     * prints a inline collection without a jump
     */
    public Print inline(Iterable<?> inline, boolean jump) {
        return _inline(inline, false, false, jump);
    }

    /**
     * prints a inline collection beggining with dashes
     */
    public Print _inline(Iterable<?> inline) {
        return _inline(inline, true, false, true);
    }

    /**
     * prints a inline collection beggining with dashes
     */
    public Print _inline(Iterable<?> inline, boolean jump) {
        return _inline(inline, true, false, jump);
    }

    /**
     * prints a inline collection inside dashes
     */
    public Print _inline_(Iterable<?> inline) {
        return _inline(inline, true, true, true);
    }

    /**
     * prints a inline collection ending with dashes
     */
    public Print inline_(Iterable<?> inline) {
        return _inline(inline, false, true, true);
    }

    /**
     * prints a inline collection ending with dashes
     */
    public Print inline_(Iterable<?> inline, boolean jump) {
        return _inline(inline, false, true, jump);
    }

    /**
     * prints what collection is given with condition determinated
     */
    private Print _inline(Iterable<?> inline, boolean init, boolean end, boolean jump) {
        if (init)
            dashes();
        for (Object o : inline) {
            _print.print(o + " ");
        }
        if (jump)
            br();
        if (end)
            dashes();
        return this;
    }

    /**
     * prints what collection is given with condition determinated
     */
    private Print _collection(Iterable<?> list, boolean init, boolean end, boolean jump) {
        if (init)
            dashes();
        for (var o : list) {
            _print.println(o);
        }
        if (jump)
            br();
        if (end)
            dashes();
        return this;
    }

    /**
     * prints what map is given with condition determinated
     */
    private Print _dictionary(Map<?, ?> map, boolean init, boolean end, boolean jump) {
        if (init)
            dashes();
        for (var o : map.keySet()) {
            _print.println(o + ":, " + map.get(o));
        }
        if (jump)
            br();
        if (end)
            dashes();
        return this;
    }

}