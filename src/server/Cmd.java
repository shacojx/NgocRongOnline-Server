package server;
public class Cmd
{
	protected static byte LOGIN = 0;

	protected static byte REGISTER = 1;

	protected static byte CLIENT_INFO = 2;

	protected static byte SEND_SMS = 3;

	protected static byte REGISTER_IMAGE = 4;

	protected static byte MESSAGE_TIME = 65;

	protected static byte LOGOUT = 0;

	protected static byte SELECT_PLAYER = 1;

	protected static byte CREATE_PLAYER = 2;

	protected static byte DELETE_PLAYER = 3;

	protected static byte UPDATE_VERSION = 4;

	protected static byte UPDATE_MAP = 6;

	protected static byte UPDATE_SKILL = 7;

	protected static byte UPDATE_ITEM = 8;

	protected static byte REQUEST_SKILL = 9;

	protected static byte REQUEST_MAPTEMPLATE = 10;

	protected static byte REQUEST_NPCTEMPLATE = 11;

	protected static byte REQUEST_NPCPLAYER = 12;

	protected static byte UPDATE_TYPE_PK = 35;

	protected static byte PLAYER_ATTACK_PLAYER = -60;

	protected static byte PLAYER_VS_PLAYER = -59;

	protected static byte CLAN_PARTY = -58;

	protected static byte CLAN_INVITE = -57;

	protected static byte CLAN_REMOTE = -56;

	protected static byte CLAN_LEAVE = -55;

	protected static byte CLAN_DONATE = -54;

	protected static byte CLAN_MESSAGE = -51;

	protected static byte CLAN_UPDATE = -52;

	protected static byte CLAN_INFO = -53;

	protected static byte CLAN_JOIN = -49;

	protected static byte CLAN_MEMBER = -50;

	protected static byte CLAN_SEARCH = -47;

	protected static byte CLAN_CREATE_INFO = -46;

	protected static byte CLIENT_OK = 13;

	protected static byte CLIENT_OK_INMAP = 14;

	protected static byte UPDATE_VERSION_OK = 15;

	protected static byte INPUT_CARD = 16;

	protected static byte CLEAR_TASK = 17;

	protected static byte CHANGE_NAME = 18;

	protected static byte UPDATE_PK = 20;

	protected static byte CREATE_CLAN = 21;

	protected static byte CONVERT_UPGRADE = 33;

	protected static byte INVITE_CLANDUN = 34;

	protected static byte NOT_USEACC = 35;

	protected static byte ME_LOAD_ACTIVE = 36;

	protected static byte ME_ACTIVE = 37;

	protected static byte ME_UPDATE_ACTIVE = 38;

	protected static byte ME_OPEN_LOCK = 39;

	protected static byte ITEM_SPLIT = 40;

	protected static byte ME_CLEAR_LOCK = 41;

	protected static byte GET_IMG_BY_NAME = 66;

	protected static byte ME_LOAD_ALL = 0;

	protected static byte ME_LOAD_CLASS = 1;

	protected static byte ME_LOAD_SKILL = 2;

	protected static byte ME_LOAD_INFO = 4;

	protected static byte ME_LOAD_HP = 5;

	protected static byte ME_LOAD_MP = 6;

	protected static byte PLAYER_LOAD_ALL = 7;

	protected static byte PLAYER_SPEED = 8;

	protected static byte PLAYER_LOAD_LEVEL = 9;

	protected static byte PLAYER_LOAD_VUKHI = 10;

	protected static byte PLAYER_LOAD_AO = 11;

	protected static byte PLAYER_LOAD_QUAN = 12;

	protected static byte PLAYER_LOAD_BODY = 13;

	protected static byte PLAYER_LOAD_HP = 14;

	protected static byte PLAYER_LOAD_LIVE = 15;

	protected static byte GOTO_PLAYER = 18;

	protected static byte POTENTIAL_UP = 16;

	protected static byte SKILL_UP = 17;

	protected static byte BAG_SORT = 18;

	protected static byte BOX_SORT = 19;

	protected static byte BOX_COIN_IN = 20;

	protected static byte BOX_COIN_OUT = 21;

	protected static byte REQUEST_ITEM = 22;

	protected static byte ME_ADD_SKILL = 23;

	protected static byte ME_UPDATE_SKILL = 62;

	protected static byte GET_PLAYER_MENU = 63;

	protected static byte PLAYER_MENU_ACTION = 64;

	protected static byte SAVE_RMS = 60;

	protected static byte LOAD_RMS = 61;

	protected static byte USE_BOOK_SKILL = 43;

	protected static byte LOCK_INVENTORY = -104;

	protected static byte CHANGE_FLAG = -103;

	protected static byte LOGINFAIL = -102;

	protected static byte LOGIN2 = -101;

	protected static byte KIGUI = -100;

	protected static byte ENEMY_LIST = -99;

	protected static byte ANDROID_IAP = -98;

	protected static byte UPDATE_ACTIVEPOINT = -97;

	protected static byte TOP = -96;

	protected static byte MOB_ME_UPDATE = -95;

	protected static byte UPDATE_COOLDOWN = -94;

	protected static byte BGITEM_VERSION = -93;

	protected static byte SET_CLIENTTYPE = -92;

	protected static byte MAP_TRASPORT = -91;

	protected static byte UPDATE_BODY = -90;

	protected static byte SERVERSCREEN = -88;

	protected static byte UPDATE_DATA = -87;

	protected static byte GIAO_DICH = -86;

	protected static byte MOB_CAPCHA = -85;

	protected static byte MOB_MAX_HP = -84;

	protected static byte CALL_DRAGON = -83;

	protected static byte TILE_SET = -82;

	protected static byte COMBINNE = -81;

	protected static byte FRIEND = -80;

	protected static byte PLAYER_MENU = -79;

	protected static byte CHECK_MOVE = -78;

	protected static byte SMALLIMAGE_VERSION = -77;

	protected static byte ARCHIVEMENT = -76;

	protected static byte NPC_BOSS = -75;

	protected static byte GET_IMAGE_SOURCE = -74;

	protected static byte NPC_ADD_REMOVE = -73;

	protected static byte CHAT_PLAYER = -72;

	protected static byte CHAT_THEGIOI_CLIENT = -71;


	protected static byte BIG_MESSAGE = -70;


	protected static byte MAXSTAMINA = -69;


	protected static byte STAMINA = -68;


	protected static byte REQUEST_ICON = -67;


	protected static byte GET_EFFDATA = -66;


	protected static byte TELEPORT = -65;


	protected static byte UPDATE_BAG = -64;


	protected static byte GET_BAG = -63;


	protected static byte CLAN_IMAGE = -62;


	protected static byte UPDATE_CLANID = -61;


	protected static byte SKILL_NOT_FOCUS = -45;


	protected static byte SHOP = -44;


	protected static byte USE_ITEM = -43;


	protected static byte ME_LOAD_POINT = -42;


	protected static byte UPDATE_CAPTION = -41;


	protected static byte GET_ITEM = -40;


	protected static byte FINISH_LOADMAP = -39;


	protected static byte FINISH_UPDATE = -38;


	protected static byte BODY = -37;


	protected static byte BAG = -36;


	protected static byte BOX = -35;


	protected static byte MAGIC_TREE = -34;


	protected static byte MAP_OFFLINE = -33;


	protected static byte BACKGROUND_TEMPLATE = -32;


	protected static byte ITEM_BACKGROUND = -31;


	protected static byte SUB_COMMAND = -30;


	protected static byte NOT_LOGIN = -29;


	protected static byte NOT_MAP = -28;


	protected static byte GET_SESSION_ID = -27;


	protected static byte DIALOG_MESSAGE = -26;


	protected static byte SERVER_MESSAGE = -25;


	protected static byte MAP_INFO = -24;


	protected static byte MAP_CHANGE = -23;


	protected static byte MAP_CLEAR = -22;


	protected static byte ITEMMAP_REMOVE = -21;


	protected static byte ITEMMAP_MYPICK = -20;


	protected static byte ITEMMAP_PLAYERPICK = -19;


	protected static byte ME_THROW = -18;


	protected static byte ME_DIE = -17;


	protected static byte ME_LIVE = -16;


	protected static byte ME_BACK = -15;


	protected static byte PLAYER_THROW = -14;


	protected static byte NPC_LIVE = -13;


	protected static byte NPC_DIE = -12;


	protected static byte NPC_ATTACK_ME = -11;


	protected static byte NPC_ATTACK_PLAYER = -10;


	protected static byte MOB_HP = -9;


	protected static byte PLAYER_DIE = -8;


	protected static byte PLAYER_MOVE = -7;


	protected static byte PLAYER_REMOVE = -6;


	protected static byte PLAYER_ADD = -5;


	protected static byte PLAYER_ATTACK_N_P = -4;


	protected static byte PLAYER_UP_EXP = -3;


	protected static byte ME_UP_COIN_LOCK = -2;


	protected static byte ME_CHANGE_COIN = -1;


	protected static byte ITEM_BUY = 6;


	protected static byte ITEM_SALE = 7;


	protected static byte UPPEARL_LOCK = 13;


	protected static byte UPGRADE = 14;


	protected static byte PLEASE_INPUT_PARTY = 16;


	protected static byte ACCEPT_PLEASE_PARTY = 17;


	protected static byte REQUEST_PLAYERS = 18;


	protected static byte UPDATE_ACHIEVEMENT = 19;


	protected static byte MOVE_FAST_NPC = 20;


	protected static byte ZONE_CHANGE = 21;


	protected static byte MENU = 22;


	protected static byte OPEN_UI = 23;


	protected static byte OPEN_UI_BOX = 24;


	protected static byte OPEN_UI_PT = 25;


	protected static byte OPEN_UI_SHOP = 26;


	protected static byte OPEN_MENU_ID = 27;


	protected static byte OPEN_UI_COLLECT = 28;


	protected static byte OPEN_UI_ZONE = 29;


	protected static byte OPEN_UI_TRADE = 30;


	protected static byte OPEN_UI_SAY = 38;


	protected static byte OPEN_UI_CONFIRM = 32;


	protected static byte OPEN_UI_MENU = 33;


	protected static byte SKILL_SELECT = 34;


	protected static byte REQUEST_ITEM_INFO = 35;


	protected static byte TRADE_INVITE = 36;


	protected static byte TRADE_INVITE_ACCEPT = 37;


	protected static byte TRADE_LOCK_ITEM = 38;


	protected static byte TRADE_ACCEPT = 39;


	protected static byte TASK_GET = 40;


	protected static byte TASK_NEXT = 41;


	protected static byte GAME_INFO = 50;


	protected static byte TASK_UPDATE = 43;


	protected static byte CHAT_MAP = 44;


	protected static byte NPC_MISS = 45;


	protected static byte RESET_POINT = 46;


	protected static byte ALERT_MESSAGE = 47;


	protected static byte AUTO_SERVER = 48;


	protected static byte ALERT_SEND_SMS = 49;


	protected static byte TRADE_INVITE_CANCEL = 50;


	protected static byte BOSS_SKILL = 51;


	protected static byte MABU_HOLD = 52;


	protected static byte FRIEND_INVITE = 53;


	protected static byte PLAYER_ATTACK_NPC = 54;


	protected static byte HAVE_ATTACK_PLAYER = 56;


	protected static byte OPEN_UI_NEWMENU = 57;


	protected static byte MOVE_FAST = 58;


	protected static byte TEST_INVITE = 59;


	protected static byte ADD_CUU_SAT = 62;


	protected static byte ME_CUU_SAT = 63;


	protected static byte CLEAR_CUU_SAT = 64;


	protected static byte PLAYER_UP_EXPDOWN = 65;


	protected static byte ME_DIE_EXP_DOWN = 66;


	protected static byte PLAYER_ATTACK_P_N = 67;


	protected static byte ITEMMAP_ADD = 68;


	protected static byte ITEM_BAG_REFRESH = 69;


	protected static byte USE_SKILL_MY_BUFF = 70;


	protected static byte NPC_CHANGE = 74;


	protected static byte PARTY_INVITE = 75;


	protected static byte PARTY_ACCEPT = 76;


	protected static byte PARTY_CANCEL = 77;


	protected static byte PLAYER_IN_PARTY = 78;


	protected static byte PARTY_OUT = 79;


	protected static byte FRIEND_ADD = 80;


	protected static byte NPC_IS_DISABLE = 81;


	protected static byte NPC_IS_MOVE = 82;


	protected static byte SUMON_ATTACK = 83;


	protected static byte RETURN_POINT_MAP = 84;


	protected static byte NPC_IS_FIRE = 85;


	protected static byte NPC_IS_ICE = 86;


	protected static byte NPC_IS_WIND = 87;


	protected static byte OPEN_TEXT_BOX_ID = 88;


	protected static byte REQUEST_ITEM_PLAYER = 90;


	protected static byte CHAT_PRIVATE = 91;


	protected static byte CHAT_THEGIOI_SERVER = 92;


	protected static byte CHAT_VIP = 93;


	protected static byte SERVER_ALERT = 94;


	protected static byte ME_UP_COIN_BAG = 95;


	protected static byte GET_TASK_ORDER = 96;


	protected static byte GET_TASK_UPDATE = 97;


	protected static byte CLEAR_TASK_ORDER = 98;


	protected static byte ADD_ITEM_MAP = 99;


	protected static byte TRANSPORT = -105;


	protected static byte ITEM_TIME = -106;


	protected static byte PET_INFO = -107;


	protected static byte PET_STATUS = -108;


	protected static byte SERVER_DATA = -110;


	protected static byte CLIENT_INPUT = -125;


	protected static byte HOLD = -124;


	protected static byte SHOW_ADS = 121;


	protected static byte LOGIN_DE = 122;


	protected static byte SET_POS = 123;


	protected static byte NPC_CHAT = 124;


	protected static byte FUSION = 125;


	protected static byte ANDROID_PACK = 126;


	protected static byte GET_IMAGE_SOURCE2 = -111;


	protected static byte CHAGE_MOD_BODY = -112;


	protected static byte CHANGE_ONSKILL = -113;


	protected static byte REQUEST_PEAN = -114;


	protected static byte POWER_INFO = -115;


	protected static byte AUTOPLAY = -116;


	protected static byte MABU = -117;


	protected static byte THACHDAU = -118;


	protected static byte THELUC = -119;


	protected static byte UPDATECHAR_MP = -123;


	protected static byte REFRESH_ITEM = 100;


	protected static byte CHECK_CONTROLLER = -120;


	protected static byte CHECK_MAP = -121;


	protected static byte BIG_BOSS = 101;


	protected static byte BIG_BOSS_2 = 102;


	protected static byte DUAHAU = -122;


	protected static byte QUAYSO = -126;


	protected static byte USER_INFO = 42;


	protected static byte OPEN3HOUR = -89;


	protected static byte STATUS_PET = 31;


	protected static byte SPEACIAL_SKILL = 112;


	protected static byte SERVER_EFFECT = 113;


	protected static byte INAPP = 114;


	protected static byte LUCKY_ROUND = -127;


	protected static byte RADA_CARD = 127;


	protected static byte CHAR_EFFECT = -128;
}
