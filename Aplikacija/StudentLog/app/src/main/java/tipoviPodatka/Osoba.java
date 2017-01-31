package tipoviPodatka;

import android.os.Parcel;
import android.os.Parcelable;

//Klasa definiranja tipa podataka za osobe
public class Osoba implements Parcelable { //klasa implementira "parcalable"  kako bi se podaci medu aktivnostima mogli slati

    public int oib;
    public String ime;
    public String prezime;
    public String korime;
    public String lozinka;
    public String uloga;

    public Osoba(){}

    protected Osoba(Parcel in) {
        oib = in.readInt();
        ime = in.readString();
        prezime = in.readString();
        korime = in.readString();
        lozinka = in.readString();
        uloga = in.readString();
    }


    //Omogucava komunikaciju izmedu aktivitija
    public static final Creator<Osoba> CREATOR = new Creator<Osoba>() {
        @Override
        public Osoba createFromParcel(Parcel in) {
            return new Osoba(in);
        }

        @Override
        public Osoba[] newArray(int size) {
            return new Osoba[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(oib);
        dest.writeString(ime);
        dest.writeString(prezime);
        dest.writeString(korime);
        dest.writeString(lozinka);
        dest.writeString(uloga);
    }
}
