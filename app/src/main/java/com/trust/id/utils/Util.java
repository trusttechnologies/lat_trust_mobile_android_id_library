package com.trust.id.utils;


import android.content.Context;
import android.graphics.Color;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;

import com.afollestad.materialdialogs.MaterialDialog;
import com.trust.id.R;
import com.trust.id.widgets.TextInputLayout;
import com.trust.id.widgets.TextInputLayoutLight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Util {
   public static MaterialDialog.Builder buildDialog(Context context, String title, String content, String positive, String negative) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        if (positive != null) builder.positiveText(positive);
        if (negative != null) builder.negativeText(negative);
        if (title != null) builder.title(title);
        if (content != null) builder.content(content);

        builder.backgroundColor(Color.WHITE);
        builder.typeface(ResourcesCompat.getFont(context, R.font.baijamjuree_regular), ResourcesCompat.getFont(context, R.font.baijamjuree_regular));
        builder.canceledOnTouchOutside(false);
        builder.cancelable(false);
        builder.contentColor(ContextCompat.getColor(context, R.color.black2_128));
        builder.titleColor(ContextCompat.getColor(context, R.color.black));

        return builder;
    }

    public static String formatDate(String currentDate) {
        try {
            //2018-12-12 18:09:12 +0000
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss '+0000'", Locale.getDefault());
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault());
            Date newDate = format.parse(currentDate);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yy - HH:mm 'hrs'", Locale.getDefault());

            TimeZone tz = TimeZone.getDefault();

            newDate.setTime(newDate.getTime() + tz.getRawOffset());
            return format2.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static String formatDateFull(String currentDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date newDate = format.parse(currentDate);

            format = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static String formatDateSimple(String currentDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date newDate = format.parse(currentDate);

            format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static String formatBirthDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Date birth = new Date(date);
        return format.format(birth);
    }

    public static String formatBirthDateServer(long date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date birth = new Date(date);
        return format.format(birth);
    }

    public static String formatBirthDate(String date) {
        SimpleDateFormat formatFinal = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        SimpleDateFormat formatInitial = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        try {
            Date birth = formatInitial.parse(date);
            return formatFinal.format(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "Error";
    }

    public static String formatBirthDateServer(String date) {
        SimpleDateFormat formatInitial = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        SimpleDateFormat formatFinal = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        try {
            Date birth = formatInitial.parse(date);
            return formatFinal.format(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "Error";
    }

    public static long getDateLong(String date) {
        SimpleDateFormat formatInitial = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        try {
            Date birth = formatInitial.parse(date);
            return birth.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

   public static TextWatcher initRutTextWatcher(final TextInputLayout input) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String clean = s.toString().replace("-", "");
                clean = filterSpecialCharacters(clean);
                String formatted;
                if (clean.length() >= 2) {
                    formatted = String.format("%s-%s", clean.substring(0, clean.length() - 1), clean.substring(clean.length() - 1));
                } else {
                    formatted = clean;
                }
                input.removeTextWatcher(this);
                s.replace(0, s.length(), formatted);
                input.addTextChangeListener(this);
            }
        };
    }

    public static TextWatcher initLightRutTextWatcher(final TextInputLayoutLight input) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String clean = s.toString().replace("-", "");
                clean = filterSpecialCharacters(clean);
                String formatted;
                if (clean.length() >= 2) {
                    formatted = String.format("%s-%s", clean.substring(0, clean.length() - 1), clean.substring(clean.length() - 1));
                } else {
                    formatted = clean;
                }

                input.removeTextWatcher(this);
                s.replace(0, s.length(), formatted);
                input.addTextChangeListener(this);
            }
        };
    }
    private static String filterSpecialCharacters(String source) {
        StringBuilder builder = new StringBuilder();
        for (char a : source.toCharArray()) {
            if (Character.isLetterOrDigit(a)) {
                builder.append(a);
            }
        }
        return builder.toString();
    }

    public static TextWatcher initBirthdayTextWatcher(final TextInputLayoutLight input) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String clean = s.toString().replace("/", "");
                String formatted;
                clean = filterSpecialCharacters(clean);

                int length = clean.length();
                if (length > 2 && length <= 4) {
                    formatted = String.format("%s/%s", clean.substring(0, 2), clean.substring(2));
                } else if (clean.length() > 4) {
                    formatted = String.format("%s/%s/%s",
                            clean.substring(0, 2),
                            clean.substring(2, 4), clean.substring(4));
                } else {
                    formatted = clean;
                }
                input.removeTextWatcher(this);
                s.replace(0, s.length(), formatted);
                input.addTextChangeListener(this);
            }
        };
    }

    public static Map<String, String> getNationalities() {
        try {
            Map<String, String> response = new LinkedHashMap<>();
            JSONArray array = new JSONArray(nationalities);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                response.put(jsonObject.getString("name"), jsonObject.getString("alpha2"));
            }
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String nationalities = "[{\"id\":4,\"name\":\"Afganistán\",\"alpha2\":\"af\",\"alpha3\":\"afg\"},\n" +
            "{\"id\":8,\"name\":\"Albania\",\"alpha2\":\"al\",\"alpha3\":\"alb\"},\n" +
            "{\"id\":276,\"name\":\"Alemania\",\"alpha2\":\"de\",\"alpha3\":\"deu\"},\n" +
            "{\"id\":20,\"name\":\"Andorra\",\"alpha2\":\"ad\",\"alpha3\":\"and\"},\n" +
            "{\"id\":24,\"name\":\"Angola\",\"alpha2\":\"ao\",\"alpha3\":\"ago\"},\n" +
            "{\"id\":28,\"name\":\"Antigua y Barbuda\",\"alpha2\":\"ag\",\"alpha3\":\"atg\"},\n" +
            "{\"id\":682,\"name\":\"Arabia Saudita\",\"alpha2\":\"sa\",\"alpha3\":\"sau\"},\n" +
            "{\"id\":12,\"name\":\"Argelia\",\"alpha2\":\"dz\",\"alpha3\":\"dza\"},\n" +
            "{\"id\":32,\"name\":\"Argentina\",\"alpha2\":\"ar\",\"alpha3\":\"arg\"},\n" +
            "{\"id\":51,\"name\":\"Armenia\",\"alpha2\":\"am\",\"alpha3\":\"arm\"},\n" +
            "{\"id\":36,\"name\":\"Australia\",\"alpha2\":\"au\",\"alpha3\":\"aus\"},\n" +
            "{\"id\":40,\"name\":\"Austria\",\"alpha2\":\"at\",\"alpha3\":\"aut\"},\n" +
            "{\"id\":31,\"name\":\"Azerbaiyán\",\"alpha2\":\"az\",\"alpha3\":\"aze\"},\n" +
            "{\"id\":44,\"name\":\"Bahamas\",\"alpha2\":\"bs\",\"alpha3\":\"bhs\"},\n" +
            "{\"id\":50,\"name\":\"Bangladés\",\"alpha2\":\"bd\",\"alpha3\":\"bgd\"},\n" +
            "{\"id\":52,\"name\":\"Barbados\",\"alpha2\":\"bb\",\"alpha3\":\"brb\"},\n" +
            "{\"id\":48,\"name\":\"Baréin\",\"alpha2\":\"bh\",\"alpha3\":\"bhr\"},\n" +
            "{\"id\":56,\"name\":\"Bélgica\",\"alpha2\":\"be\",\"alpha3\":\"bel\"},\n" +
            "{\"id\":84,\"name\":\"Belice\",\"alpha2\":\"bz\",\"alpha3\":\"blz\"},\n" +
            "{\"id\":204,\"name\":\"Benín\",\"alpha2\":\"bj\",\"alpha3\":\"ben\"},\n" +
            "{\"id\":112,\"name\":\"Bielorrusia\",\"alpha2\":\"by\",\"alpha3\":\"blr\"},\n" +
            "{\"id\":68,\"name\":\"Bolivia\",\"alpha2\":\"bo\",\"alpha3\":\"bol\"},\n" +
            "{\"id\":70,\"name\":\"Bosnia y Herzegovina\",\"alpha2\":\"ba\",\"alpha3\":\"bih\"},\n" +
            "{\"id\":72,\"name\":\"Botsuana\",\"alpha2\":\"bw\",\"alpha3\":\"bwa\"},\n" +
            "{\"id\":76,\"name\":\"Brasil\",\"alpha2\":\"br\",\"alpha3\":\"bra\"},\n" +
            "{\"id\":96,\"name\":\"Brunéi\",\"alpha2\":\"bn\",\"alpha3\":\"brn\"},\n" +
            "{\"id\":100,\"name\":\"Bulgaria\",\"alpha2\":\"bg\",\"alpha3\":\"bgr\"},\n" +
            "{\"id\":854,\"name\":\"Burkina Faso\",\"alpha2\":\"bf\",\"alpha3\":\"bfa\"},\n" +
            "{\"id\":108,\"name\":\"Burundi\",\"alpha2\":\"bi\",\"alpha3\":\"bdi\"},\n" +
            "{\"id\":64,\"name\":\"Bután\",\"alpha2\":\"bt\",\"alpha3\":\"btn\"},\n" +
            "{\"id\":132,\"name\":\"Cabo Verde\",\"alpha2\":\"cv\",\"alpha3\":\"cpv\"},\n" +
            "{\"id\":116,\"name\":\"Camboya\",\"alpha2\":\"kh\",\"alpha3\":\"khm\"},\n" +
            "{\"id\":120,\"name\":\"Camerún\",\"alpha2\":\"cm\",\"alpha3\":\"cmr\"},\n" +
            "{\"id\":124,\"name\":\"Canadá\",\"alpha2\":\"ca\",\"alpha3\":\"can\"},\n" +
            "{\"id\":634,\"name\":\"Catar\",\"alpha2\":\"qa\",\"alpha3\":\"qat\"},\n" +
            "{\"id\":148,\"name\":\"Chad\",\"alpha2\":\"td\",\"alpha3\":\"tcd\"},\n" +
            "{\"id\":152,\"name\":\"Chile\",\"alpha2\":\"cl\",\"alpha3\":\"chl\"},\n" +
            "{\"id\":156,\"name\":\"China\",\"alpha2\":\"cn\",\"alpha3\":\"chn\"},\n" +
            "{\"id\":196,\"name\":\"Chipre\",\"alpha2\":\"cy\",\"alpha3\":\"cyp\"},\n" +
            "{\"id\":170,\"name\":\"Colombia\",\"alpha2\":\"co\",\"alpha3\":\"col\"},\n" +
            "{\"id\":174,\"name\":\"Comoras\",\"alpha2\":\"km\",\"alpha3\":\"com\"},\n" +
            "{\"id\":408,\"name\":\"Corea del Norte\",\"alpha2\":\"kp\",\"alpha3\":\"prk\"},\n" +
            "{\"id\":410,\"name\":\"Corea del Sur\",\"alpha2\":\"kr\",\"alpha3\":\"kor\"},\n" +
            "{\"id\":384,\"name\":\"Costa de Marfil\",\"alpha2\":\"ci\",\"alpha3\":\"civ\"},\n" +
            "{\"id\":188,\"name\":\"Costa Rica\",\"alpha2\":\"cr\",\"alpha3\":\"cri\"},\n" +
            "{\"id\":191,\"name\":\"Croacia\",\"alpha2\":\"hr\",\"alpha3\":\"hrv\"},\n" +
            "{\"id\":192,\"name\":\"Cuba\",\"alpha2\":\"cu\",\"alpha3\":\"cub\"},\n" +
            "{\"id\":208,\"name\":\"Dinamarca\",\"alpha2\":\"dk\",\"alpha3\":\"dnk\"},\n" +
            "{\"id\":212,\"name\":\"Dominica\",\"alpha2\":\"dm\",\"alpha3\":\"dma\"},\n" +
            "{\"id\":218,\"name\":\"Ecuador\",\"alpha2\":\"ec\",\"alpha3\":\"ecu\"},\n" +
            "{\"id\":818,\"name\":\"Egipto\",\"alpha2\":\"eg\",\"alpha3\":\"egy\"},\n" +
            "{\"id\":222,\"name\":\"El Salvador\",\"alpha2\":\"sv\",\"alpha3\":\"slv\"},\n" +
            "{\"id\":784,\"name\":\"Emiratos Árabes Unidos\",\"alpha2\":\"ae\",\"alpha3\":\"are\"},\n" +
            "{\"id\":232,\"name\":\"Eritrea\",\"alpha2\":\"er\",\"alpha3\":\"eri\"},\n" +
            "{\"id\":703,\"name\":\"Eslovaquia\",\"alpha2\":\"sk\",\"alpha3\":\"svk\"},\n" +
            "{\"id\":705,\"name\":\"Eslovenia\",\"alpha2\":\"si\",\"alpha3\":\"svn\"},\n" +
            "{\"id\":724,\"name\":\"España\",\"alpha2\":\"es\",\"alpha3\":\"esp\"},\n" +
            "{\"id\":840,\"name\":\"Estados Unidos\",\"alpha2\":\"us\",\"alpha3\":\"usa\"},\n" +
            "{\"id\":233,\"name\":\"Estonia\",\"alpha2\":\"ee\",\"alpha3\":\"est\"},\n" +
            "{\"id\":231,\"name\":\"Etiopía\",\"alpha2\":\"et\",\"alpha3\":\"eth\"},\n" +
            "{\"id\":608,\"name\":\"Filipinas\",\"alpha2\":\"ph\",\"alpha3\":\"phl\"},\n" +
            "{\"id\":246,\"name\":\"Finlandia\",\"alpha2\":\"fi\",\"alpha3\":\"fin\"},\n" +
            "{\"id\":242,\"name\":\"Fiyi\",\"alpha2\":\"fj\",\"alpha3\":\"fji\"},\n" +
            "{\"id\":250,\"name\":\"Francia\",\"alpha2\":\"fr\",\"alpha3\":\"fra\"},\n" +
            "{\"id\":266,\"name\":\"Gabón\",\"alpha2\":\"ga\",\"alpha3\":\"gab\"},\n" +
            "{\"id\":270,\"name\":\"Gambia\",\"alpha2\":\"gm\",\"alpha3\":\"gmb\"},\n" +
            "{\"id\":268,\"name\":\"Georgia\",\"alpha2\":\"ge\",\"alpha3\":\"geo\"},\n" +
            "{\"id\":288,\"name\":\"Ghana\",\"alpha2\":\"gh\",\"alpha3\":\"gha\"},\n" +
            "{\"id\":308,\"name\":\"Granada\",\"alpha2\":\"gd\",\"alpha3\":\"grd\"},\n" +
            "{\"id\":300,\"name\":\"Grecia\",\"alpha2\":\"gr\",\"alpha3\":\"grc\"},\n" +
            "{\"id\":320,\"name\":\"Guatemala\",\"alpha2\":\"gt\",\"alpha3\":\"gtm\"},\n" +
            "{\"id\":324,\"name\":\"Guinea\",\"alpha2\":\"gn\",\"alpha3\":\"gin\"},\n" +
            "{\"id\":624,\"name\":\"Guinea-Bisáu\",\"alpha2\":\"gw\",\"alpha3\":\"gnb\"},\n" +
            "{\"id\":226,\"name\":\"Guinea Ecuatorial\",\"alpha2\":\"gq\",\"alpha3\":\"gnq\"},\n" +
            "{\"id\":328,\"name\":\"Guyana\",\"alpha2\":\"gy\",\"alpha3\":\"guy\"},\n" +
            "{\"id\":332,\"name\":\"Haití\",\"alpha2\":\"ht\",\"alpha3\":\"hti\"},\n" +
            "{\"id\":340,\"name\":\"Honduras\",\"alpha2\":\"hn\",\"alpha3\":\"hnd\"},\n" +
            "{\"id\":348,\"name\":\"Hungría\",\"alpha2\":\"hu\",\"alpha3\":\"hun\"},\n" +
            "{\"id\":356,\"name\":\"India\",\"alpha2\":\"in\",\"alpha3\":\"ind\"},\n" +
            "{\"id\":360,\"name\":\"Indonesia\",\"alpha2\":\"id\",\"alpha3\":\"idn\"},\n" +
            "{\"id\":368,\"name\":\"Irak\",\"alpha2\":\"iq\",\"alpha3\":\"irq\"},\n" +
            "{\"id\":364,\"name\":\"Irán\",\"alpha2\":\"ir\",\"alpha3\":\"irn\"},\n" +
            "{\"id\":372,\"name\":\"Irlanda\",\"alpha2\":\"ie\",\"alpha3\":\"irl\"},\n" +
            "{\"id\":352,\"name\":\"Islandia\",\"alpha2\":\"is\",\"alpha3\":\"isl\"},\n" +
            "{\"id\":584,\"name\":\"Islas Marshall\",\"alpha2\":\"mh\",\"alpha3\":\"mhl\"},\n" +
            "{\"id\":90,\"name\":\"Islas Salomón\",\"alpha2\":\"sb\",\"alpha3\":\"slb\"},\n" +
            "{\"id\":376,\"name\":\"Israel\",\"alpha2\":\"il\",\"alpha3\":\"isr\"},\n" +
            "{\"id\":380,\"name\":\"Italia\",\"alpha2\":\"it\",\"alpha3\":\"ita\"},\n" +
            "{\"id\":388,\"name\":\"Jamaica\",\"alpha2\":\"jm\",\"alpha3\":\"jam\"},\n" +
            "{\"id\":392,\"name\":\"Japón\",\"alpha2\":\"jp\",\"alpha3\":\"jpn\"},\n" +
            "{\"id\":400,\"name\":\"Jordania\",\"alpha2\":\"jo\",\"alpha3\":\"jor\"},\n" +
            "{\"id\":398,\"name\":\"Kazajistán\",\"alpha2\":\"kz\",\"alpha3\":\"kaz\"},\n" +
            "{\"id\":404,\"name\":\"Kenia\",\"alpha2\":\"ke\",\"alpha3\":\"ken\"},\n" +
            "{\"id\":417,\"name\":\"Kirguistán\",\"alpha2\":\"kg\",\"alpha3\":\"kgz\"},\n" +
            "{\"id\":296,\"name\":\"Kiribati\",\"alpha2\":\"ki\",\"alpha3\":\"kir\"},\n" +
            "{\"id\":414,\"name\":\"Kuwait\",\"alpha2\":\"kw\",\"alpha3\":\"kwt\"},\n" +
            "{\"id\":418,\"name\":\"Laos\",\"alpha2\":\"la\",\"alpha3\":\"lao\"},\n" +
            "{\"id\":426,\"name\":\"Lesoto\",\"alpha2\":\"ls\",\"alpha3\":\"lso\"},\n" +
            "{\"id\":428,\"name\":\"Letonia\",\"alpha2\":\"lv\",\"alpha3\":\"lva\"},\n" +
            "{\"id\":422,\"name\":\"Líbano\",\"alpha2\":\"lb\",\"alpha3\":\"lbn\"},\n" +
            "{\"id\":430,\"name\":\"Liberia\",\"alpha2\":\"lr\",\"alpha3\":\"lbr\"},\n" +
            "{\"id\":434,\"name\":\"Libia\",\"alpha2\":\"ly\",\"alpha3\":\"lby\"},\n" +
            "{\"id\":438,\"name\":\"Liechtenstein\",\"alpha2\":\"li\",\"alpha3\":\"lie\"},\n" +
            "{\"id\":440,\"name\":\"Lituania\",\"alpha2\":\"lt\",\"alpha3\":\"ltu\"},\n" +
            "{\"id\":442,\"name\":\"Luxemburgo\",\"alpha2\":\"lu\",\"alpha3\":\"lux\"},\n" +
            "{\"id\":807,\"name\":\"Macedonia\",\"alpha2\":\"mk\",\"alpha3\":\"mkd\"},\n" +
            "{\"id\":450,\"name\":\"Madagascar\",\"alpha2\":\"mg\",\"alpha3\":\"mdg\"},\n" +
            "{\"id\":458,\"name\":\"Malasia\",\"alpha2\":\"my\",\"alpha3\":\"mys\"},\n" +
            "{\"id\":454,\"name\":\"Malaui\",\"alpha2\":\"mw\",\"alpha3\":\"mwi\"},\n" +
            "{\"id\":462,\"name\":\"Maldivas\",\"alpha2\":\"mv\",\"alpha3\":\"mdv\"},\n" +
            "{\"id\":466,\"name\":\"Malí\",\"alpha2\":\"ml\",\"alpha3\":\"mli\"},\n" +
            "{\"id\":470,\"name\":\"Malta\",\"alpha2\":\"mt\",\"alpha3\":\"mlt\"},\n" +
            "{\"id\":504,\"name\":\"Marruecos\",\"alpha2\":\"ma\",\"alpha3\":\"mar\"},\n" +
            "{\"id\":480,\"name\":\"Mauricio\",\"alpha2\":\"mu\",\"alpha3\":\"mus\"},\n" +
            "{\"id\":478,\"name\":\"Mauritania\",\"alpha2\":\"mr\",\"alpha3\":\"mrt\"},\n" +
            "{\"id\":484,\"name\":\"México\",\"alpha2\":\"mx\",\"alpha3\":\"mex\"},\n" +
            "{\"id\":583,\"name\":\"Micronesia\",\"alpha2\":\"fm\",\"alpha3\":\"fsm\"},\n" +
            "{\"id\":498,\"name\":\"Moldavia\",\"alpha2\":\"md\",\"alpha3\":\"mda\"},\n" +
            "{\"id\":492,\"name\":\"Mónaco\",\"alpha2\":\"mc\",\"alpha3\":\"mco\"},\n" +
            "{\"id\":496,\"name\":\"Mongolia\",\"alpha2\":\"mn\",\"alpha3\":\"mng\"},\n" +
            "{\"id\":499,\"name\":\"Montenegro\",\"alpha2\":\"me\",\"alpha3\":\"mne\"},\n" +
            "{\"id\":508,\"name\":\"Mozambique\",\"alpha2\":\"mz\",\"alpha3\":\"moz\"},\n" +
            "{\"id\":104,\"name\":\"Myanmar\",\"alpha2\":\"mm\",\"alpha3\":\"mmr\"},\n" +
            "{\"id\":516,\"name\":\"Namibia\",\"alpha2\":\"na\",\"alpha3\":\"nam\"},\n" +
            "{\"id\":520,\"name\":\"Nauru\",\"alpha2\":\"nr\",\"alpha3\":\"nru\"},\n" +
            "{\"id\":524,\"name\":\"Nepal\",\"alpha2\":\"np\",\"alpha3\":\"npl\"},\n" +
            "{\"id\":558,\"name\":\"Nicaragua\",\"alpha2\":\"ni\",\"alpha3\":\"nic\"},\n" +
            "{\"id\":562,\"name\":\"Níger\",\"alpha2\":\"ne\",\"alpha3\":\"ner\"},\n" +
            "{\"id\":566,\"name\":\"Nigeria\",\"alpha2\":\"ng\",\"alpha3\":\"nga\"},\n" +
            "{\"id\":578,\"name\":\"Noruega\",\"alpha2\":\"no\",\"alpha3\":\"nor\"},\n" +
            "{\"id\":554,\"name\":\"Nueva Zelanda\",\"alpha2\":\"nz\",\"alpha3\":\"nzl\"},\n" +
            "{\"id\":512,\"name\":\"Omán\",\"alpha2\":\"om\",\"alpha3\":\"omn\"},\n" +
            "{\"id\":528,\"name\":\"Países Bajos\",\"alpha2\":\"nl\",\"alpha3\":\"nld\"},\n" +
            "{\"id\":586,\"name\":\"Pakistán\",\"alpha2\":\"pk\",\"alpha3\":\"pak\"},\n" +
            "{\"id\":585,\"name\":\"Palaos\",\"alpha2\":\"pw\",\"alpha3\":\"plw\"},\n" +
            "{\"id\":591,\"name\":\"Panamá\",\"alpha2\":\"pa\",\"alpha3\":\"pan\"},\n" +
            "{\"id\":598,\"name\":\"Papúa Nueva Guinea\",\"alpha2\":\"pg\",\"alpha3\":\"png\"},\n" +
            "{\"id\":600,\"name\":\"Paraguay\",\"alpha2\":\"py\",\"alpha3\":\"pry\"},\n" +
            "{\"id\":604,\"name\":\"Perú\",\"alpha2\":\"pe\",\"alpha3\":\"per\"},\n" +
            "{\"id\":616,\"name\":\"Polonia\",\"alpha2\":\"pl\",\"alpha3\":\"pol\"},\n" +
            "{\"id\":620,\"name\":\"Portugal\",\"alpha2\":\"pt\",\"alpha3\":\"prt\"},\n" +
            "{\"id\":826,\"name\":\"Reino Unido\",\"alpha2\":\"gb\",\"alpha3\":\"gbr\"},\n" +
            "{\"id\":140,\"name\":\"República Centroafricana\",\"alpha2\":\"cf\",\"alpha3\":\"caf\"},\n" +
            "{\"id\":203,\"name\":\"República Checa\",\"alpha2\":\"cz\",\"alpha3\":\"cze\"},\n" +
            "{\"id\":178,\"name\":\"República del Congo\",\"alpha2\":\"cg\",\"alpha3\":\"cog\"},\n" +
            "{\"id\":180,\"name\":\"República Democrática del Congo\",\"alpha2\":\"cd\",\"alpha3\":\"cod\"},\n" +
            "{\"id\":214,\"name\":\"República Dominicana\",\"alpha2\":\"do\",\"alpha3\":\"dom\"},\n" +
            "{\"id\":646,\"name\":\"Ruanda\",\"alpha2\":\"rw\",\"alpha3\":\"rwa\"},\n" +
            "{\"id\":642,\"name\":\"Rumania\",\"alpha2\":\"ro\",\"alpha3\":\"rou\"},\n" +
            "{\"id\":643,\"name\":\"Rusia\",\"alpha2\":\"ru\",\"alpha3\":\"rus\"},\n" +
            "{\"id\":882,\"name\":\"Samoa\",\"alpha2\":\"ws\",\"alpha3\":\"wsm\"},\n" +
            "{\"id\":659,\"name\":\"San Cristóbal y Nieves\",\"alpha2\":\"kn\",\"alpha3\":\"kna\"},\n" +
            "{\"id\":674,\"name\":\"San Marino\",\"alpha2\":\"sm\",\"alpha3\":\"smr\"},\n" +
            "{\"id\":670,\"name\":\"San Vicente y las Granadinas\",\"alpha2\":\"vc\",\"alpha3\":\"vct\"},\n" +
            "{\"id\":662,\"name\":\"Santa Lucía\",\"alpha2\":\"lc\",\"alpha3\":\"lca\"},\n" +
            "{\"id\":678,\"name\":\"Santo Tomé y Príncipe\",\"alpha2\":\"st\",\"alpha3\":\"stp\"},\n" +
            "{\"id\":686,\"name\":\"Senegal\",\"alpha2\":\"sn\",\"alpha3\":\"sen\"},\n" +
            "{\"id\":688,\"name\":\"Serbia\",\"alpha2\":\"rs\",\"alpha3\":\"srb\"},\n" +
            "{\"id\":690,\"name\":\"Seychelles\",\"alpha2\":\"sc\",\"alpha3\":\"syc\"},\n" +
            "{\"id\":694,\"name\":\"Sierra Leona\",\"alpha2\":\"sl\",\"alpha3\":\"sle\"},\n" +
            "{\"id\":702,\"name\":\"Singapur\",\"alpha2\":\"sg\",\"alpha3\":\"sgp\"},\n" +
            "{\"id\":760,\"name\":\"Siria\",\"alpha2\":\"sy\",\"alpha3\":\"syr\"},\n" +
            "{\"id\":706,\"name\":\"Somalia\",\"alpha2\":\"so\",\"alpha3\":\"som\"},\n" +
            "{\"id\":144,\"name\":\"Sri Lanka\",\"alpha2\":\"lk\",\"alpha3\":\"lka\"},\n" +
            "{\"id\":748,\"name\":\"Suazilandia\",\"alpha2\":\"sz\",\"alpha3\":\"swz\"},\n" +
            "{\"id\":710,\"name\":\"Sudáfrica\",\"alpha2\":\"za\",\"alpha3\":\"zaf\"},\n" +
            "{\"id\":729,\"name\":\"Sudán\",\"alpha2\":\"sd\",\"alpha3\":\"sdn\"},\n" +
            "{\"id\":728,\"name\":\"Sudán del Sur\",\"alpha2\":\"ss\",\"alpha3\":\"ssd\"},\n" +
            "{\"id\":752,\"name\":\"Suecia\",\"alpha2\":\"se\",\"alpha3\":\"swe\"},\n" +
            "{\"id\":756,\"name\":\"Suiza\",\"alpha2\":\"ch\",\"alpha3\":\"che\"},\n" +
            "{\"id\":740,\"name\":\"Surinam\",\"alpha2\":\"sr\",\"alpha3\":\"sur\"},\n" +
            "{\"id\":764,\"name\":\"Tailandia\",\"alpha2\":\"th\",\"alpha3\":\"tha\"},\n" +
            "{\"id\":834,\"name\":\"Tanzania\",\"alpha2\":\"tz\",\"alpha3\":\"tza\"},\n" +
            "{\"id\":762,\"name\":\"Tayikistán\",\"alpha2\":\"tj\",\"alpha3\":\"tjk\"},\n" +
            "{\"id\":626,\"name\":\"Timor Oriental\",\"alpha2\":\"tl\",\"alpha3\":\"tls\"},\n" +
            "{\"id\":768,\"name\":\"Togo\",\"alpha2\":\"tg\",\"alpha3\":\"tgo\"},\n" +
            "{\"id\":776,\"name\":\"Tonga\",\"alpha2\":\"to\",\"alpha3\":\"ton\"},\n" +
            "{\"id\":780,\"name\":\"Trinidad y Tobago\",\"alpha2\":\"tt\",\"alpha3\":\"tto\"},\n" +
            "{\"id\":788,\"name\":\"Túnez\",\"alpha2\":\"tn\",\"alpha3\":\"tun\"},\n" +
            "{\"id\":795,\"name\":\"Turkmenistán\",\"alpha2\":\"tm\",\"alpha3\":\"tkm\"},\n" +
            "{\"id\":792,\"name\":\"Turquía\",\"alpha2\":\"tr\",\"alpha3\":\"tur\"},\n" +
            "{\"id\":798,\"name\":\"Tuvalu\",\"alpha2\":\"tv\",\"alpha3\":\"tuv\"},\n" +
            "{\"id\":804,\"name\":\"Ucrania\",\"alpha2\":\"ua\",\"alpha3\":\"ukr\"},\n" +
            "{\"id\":800,\"name\":\"Uganda\",\"alpha2\":\"ug\",\"alpha3\":\"uga\"},\n" +
            "{\"id\":858,\"name\":\"Uruguay\",\"alpha2\":\"uy\",\"alpha3\":\"ury\"},\n" +
            "{\"id\":860,\"name\":\"Uzbekistán\",\"alpha2\":\"uz\",\"alpha3\":\"uzb\"},\n" +
            "{\"id\":548,\"name\":\"Vanuatu\",\"alpha2\":\"vu\",\"alpha3\":\"vut\"},\n" +
            "{\"id\":862,\"name\":\"Venezuela\",\"alpha2\":\"ve\",\"alpha3\":\"ven\"},\n" +
            "{\"id\":704,\"name\":\"Vietnam\",\"alpha2\":\"vn\",\"alpha3\":\"vnm\"},\n" +
            "{\"id\":887,\"name\":\"Yemen\",\"alpha2\":\"ye\",\"alpha3\":\"yem\"},\n" +
            "{\"id\":262,\"name\":\"Yibuti\",\"alpha2\":\"dj\",\"alpha3\":\"dji\"},\n" +
            "{\"id\":894,\"name\":\"Zambia\",\"alpha2\":\"zm\",\"alpha3\":\"zmb\"},\n" +
            "{\"id\":716,\"name\":\"Zimbabue\",\"alpha2\":\"zw\",\"alpha3\":\"zwe\"}\n" +
            "]";

}
