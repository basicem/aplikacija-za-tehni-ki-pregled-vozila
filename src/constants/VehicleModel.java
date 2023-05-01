package constants;

import enums.VehicleBrand;

import java.util.ArrayList;
import java.util.HashMap;

public class VehicleModel {

    public static HashMap<VehicleBrand, ArrayList<String>> listFromBrands = new HashMap<>()
    {{
        put(VehicleBrand.Suzuki, new ArrayList<>() {{
            add("Vitara");
            add("Ignis");
        }});
        put(VehicleBrand.Fiat, new ArrayList<>() {{
            add("Panda");
            add("Coupe");
        }});
        put(VehicleBrand.Volvo, new ArrayList<>() {{
            add("XC40");
            add("S60");
        }});
        put(VehicleBrand.Volkswagen, new ArrayList<>() {{
            add("Polo");
            add("Touareg");
        }});
    }};

}
// VehicleModel.listaModelaPremaTipu.get(KLJUC_OD_VOZILA)