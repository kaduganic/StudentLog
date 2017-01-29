package tipoviPodatka;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;

/**
 * Created by Nikola on 1.12.2016..
 */

public class Kolegiji implements Parcelable {

    public Kolegiji(){}

    public int id;
    public String naziv;
    public int ects;
    public int idNositelj;
    public String opisKolegija;
    public String uvijeti;

    public Kolegiji(int i, String n, int e, int idNos) {
        id = i;
        naziv = n;
        ects = e;
        idNositelj = idNos;
    }

    protected Kolegiji(Parcel in) {
        id = in.readInt();
        naziv = in.readString();
        ects = in.readInt();
        idNositelj = in.readInt();
        opisKolegija = in.readString();
        uvijeti = in.readString();
    }

    public static final Creator<Kolegiji> CREATOR = new Creator<Kolegiji>() {
        @Override
        public Kolegiji createFromParcel(Parcel in) {
            return new Kolegiji(in);
        }

        @Override
        public Kolegiji[] newArray(int size) {
            return new Kolegiji[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(naziv);
        dest.writeInt(ects);
        dest.writeInt(idNositelj);
        dest.writeString(opisKolegija);
        dest.writeString(uvijeti);
    }
}
