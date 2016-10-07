package es.rubenjgarcia.commons.functional.tuple;

public class Tuple3<T1, T2, T3> {
    public final T1 _1;
    public final T2 _2;
    public final T3 _3;

    Tuple3(T1 _1, T2 _2, T3 _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    @Override
    public String toString() {
        return "(" + _1 + ", " + _2 + ", " + _3 + ")";
    }
}
