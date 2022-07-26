package server;

public class Text {
    public static String get(int type, int num) {
        try {
            if (type == 0) {
                return TEXTVIE[num];
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return String.format("ERROR[%d:%d]", new Object[]{Integer.valueOf(type), Integer.valueOf(num)});
    }
    public static String[] getUI(int type, int num) {
        try {
            if (type == 0) {
                return uiShop[num];
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return uiShop[num];
    }
    private static final String[] TEXTVIE = new String[]{
            "Con cố gắng theo Vua Vegata học thành tài, đừng lo cho ta.",
            "Cậu cần trang bị gì cứ đến chỗ tôi nhé."

    };
    public  static final String[][] uiShop = new String[][]{
    {"Áo Quần","Phụ Kiện","Đặc Biệt"},
    {"Áo Quần","Phụ Kiện"}
    };
}