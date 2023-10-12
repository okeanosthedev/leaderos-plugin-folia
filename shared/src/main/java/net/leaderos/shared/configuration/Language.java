package net.leaderos.shared.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * @author poyrazinan
 * @since 1.0
 */
@Getter @Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class Language  extends OkaeriConfig {

    /**
     * Settings menu of config
     */
    @Comment("Main settings")
    private Messages messages = new Messages();

    /**
     * Messages of plugin
     * @author poyrazinan
     * @since 1.0
     */
    @Getter
    @Setter
    public static class Messages extends OkaeriConfig {

        /**
         * Prefix of plugin TODO Explain
         */
        private String prefix = "&3LeaderOS &8»";

        private String creditInfo = "{prefix} &aYou have &e{amount} &acredit(s)";

        private String creditInfoOther = "{prefix} &b{target} &ahas &e{amount} &acredit(s)";

        private String cannotSendCreditYourself = "{prefix} &cYou can not send credit(s) to yourself.";

        private String cannotSendCreditNegative = "{prefix} &cPlease enter a valid amount. The amount must be higher than 0.";

        private String cannotSendCreditNotEnough = "{prefix} &cYou do not have enough credit(s).";

        private String playerNotOnline = "{prefix} &cTarget player is not online.";

        private String playerNotAvailable = "{prefix} &cPlayer is not available.";

        private String targetPlayerNotAvailable = "{prefix} &cTarget player is not available.";

        private String successfullySentCredit = "{prefix} &aSuccessfully sent &e{amount} credit(s) &ato &b{target}&a.";

        private String successfullySetCredit = "{prefix} &aSuccessfully set credits to &e{amount} &afor &b{target}&a.";

        private String successfullyAddedCredit = "{prefix} &aSuccessfully added &e{amount} credit &ato &b{target}&a.";

        private String successfullyRemovedCredit = "{prefix} &aSuccessfully removed &e{amount} credit &afrom &b{target}&a.";

        private String receivedCredit = "{prefix} &aYou just received &e{amount} credit(s) &afrom &e{player}.";

        /**
         * Help commands message
         */
        @Comment("Help commands message")
        private List<String> help = Arrays.asList(
                "&6&l  LEADEROS PLUGIN'S COMMANDS",
                "",
                "&8 ▪ &e/webshop &8» &fOpens the WebShop menu.",
                "&8 ▪ &e/webshop server <serverName> &8» &fShows the server in the Webshop menu.",
                "&8 ▪ &e/webshop servers &8» &fShows all servers in the Webshop menu.",
                "",
                "&8 ▪ &e/creditsvoucher give <player> <amount> &8» &fGives the player a credit voucher.",
                "&8 ▪ &e/creditsvoucher create <amount> &8» &fCreates a credit voucher.",
                "",
                "&8 ▪ &e/credits &8» &fShows your credit amount.",
                "&8 ▪ &e/credits see <target> &8» &fShows the player credit amount.",
                "&8 ▪ &e/credits send <target> <amount> &8» &fSends credits to the player.",
                "&8 ▪ &e/credits set <target> <amount> &8» &fSets the player's credits.",
                "&8 ▪ &e/credits remove <target> <amount> &8» &fRemoves credits from the player.",
                "&8 ▪ &e/credits add <target> <amount> &8» &fAdds credits to the player.",
                "",
                "&8 ▪ &e/leaderos reload &8» &fReloads the config."
        );

        /**
         * Auth messages
         */
        private Auth auth = new Auth();

        /**
         * Auth messages of plugin
         */
        @Getter @Setter
        public static class Auth extends OkaeriConfig {

            /**
             * Command Message
             */
            private String commandMessage = "<&aFor authentication click here!{&5Click Me!}(open_url:%link%)>";

            /**
             * Module error message
             */
            private String moduleError = "<&cThis system require you to login website, click here!{&5Click Me!}(open_url:%link%)>";
        }

        /**
         * Info messages
         */
        private Info info = new Info();

        /**
         * Info messages of plugin
         *
         * @author poyrazinan
         * @since 1.0
         */
        @Getter
        @Setter
        public static class Info extends OkaeriConfig {

            /**
             * Module enabled message
             */
            private String moduleEnabled = "{prefix} &a%module_name% enabled.";

            /**
             * Module closed message
             */
            private String moduleClosed = "{prefix} &c%module_name% closed.";

            /**
             * Module disabled message
             */
            private String moduleDisabled = "{prefix} &4%module_name% disabled.";

            /**
             * Missing dependency message
             */
            private String missingDependency = "{prefix} &4%module_name% has not started due missing dependency. &c[%dependencies%]";
        }

        /**
         * Command object
         */
        private Command command = new Command();

        /**
         * Command arguments class
         */
        @Getter @Setter
        public static class Command extends OkaeriConfig {

            /**
             * Invalid argument message
             */
            private String invalidArgument = "{prefix} &cInvalid argument!";

            /**
             * Unknown command message
             */
            private String unknownCommand = "{prefix} &cUnknown command!";

            /**
             * Not enough arguments message
             */
            private String notEnoughArguments = "{prefix} &cNot enough arguments!";

            /**
             * too many arguments message
             */
            private String tooManyArguments = "{prefix} &cToo many arguments!";

            /**
             * no perm message
             */
            private String noPerm = "{prefix} &cYou do not have permission to do this action!";

        }
    }


    /**
     * Gui object
     */
    private Gui gui = new Gui();

    /**
     * Gui arguments class
     */
    @Getter
    @Setter
    public static class Gui extends OkaeriConfig {

        /**
         * Default gui object
         */
        private DefaultGui defaultGui = new DefaultGui();

        /**
         * Default Gui arguments class
         */
        @Getter @Setter
        public static class DefaultGui extends OkaeriConfig {

            /**
             * Filler item object
             */
            private FillerItem fillerItem = new FillerItem();

            /**
             * Filler item arguments class
             */
            @Getter @Setter
            public static class FillerItem extends OkaeriConfig {
                /**
                 * material of item
                 */
                private String material = "GRAY_STAINED_GLASS_PANE";
                /**
                 * status of filler
                 */
                @Comment("Fills empty areas")
                private boolean useFiller = true;
            }

            /**
             * PreviousPage item object
             */
            private PreviousPage previousPage = new PreviousPage();

            /**
             * PreviousPage item arguments class
             */
            @Getter @Setter
            public static class PreviousPage extends OkaeriConfig {
                /**
                 * material of item
                 */
                private String name = "&aPrevious Page";
            }

            /**
             * PreviousPage item object
             */
            private NextPage nextPage = new NextPage();

            /**
             * PreviousPage item arguments class
             */
            @Getter @Setter
            public static class NextPage extends OkaeriConfig {
                /**
                 * material of item
                 */
                private String name = "&aNext Page";
            }

            /**
             * Default Category attributes
             */
            private DefaultCategory defaultCategory = new DefaultCategory();

            /**
             * Default Category arguments class
             */
            @Getter @Setter
            public static class DefaultCategory extends OkaeriConfig {

                /**
                 * Default material
                 */
                private String material = "DIAMOND_BLOCK";

                /**
                 * Default Lore
                 */
                private List<String> lore = Arrays.asList(
                        "&r",
                        "&aClick for open category!"
                );
            }

            /**
             * DefaultProduct attributes
             */
            private DefaultProduct defaultProduct = new DefaultProduct();

            /**
             * DefaultProduct arguments class
             */
            @Getter @Setter
            public static class DefaultProduct extends OkaeriConfig {

                /**
                 * Default material
                 */
                private String material = "DIAMOND";

                /**
                 * Default Lore
                 */
                private List<String> lore = Arrays.asList(
                        "&r",
                        "&7Price &8» &e%price%",
                        "&7Stock &8» &e%stock%",
                        "",
                        "&aClick for buy!"
                );
            }

        }

        /**
         * WebStore gui object
         */
        private WebStoreGui webStoreGui = new WebStoreGui();

        /**
         * WebStore gui arguments class
         */
        @Getter @Setter
        public static class WebStoreGui extends OkaeriConfig {

            /**
             * landing layout of gui
             */
            @Comment("Layout of gui")
            private List<String> landingGuiLayout = Arrays.asList(
                    "         ",
                    "   ccc   ",
                    "         ",
                    "b       n"
            );

            /**
             * layout of gui
             */
            @Comment("Layout of gui")
            private List<String> layout = Arrays.asList(
                    "         ",
                    "   ccc   ",
                    "         ",
                    "  ppppp  ",
                    "         ",
                    "b       n"
            );

            /**
             * name of gui
             */
            private String guiName = "&8WebStore";

            /**
             * Discounted price format
             */
            private String discountedPriceFormat = "&c&m{price}&r &a{discountedPrice}";

            /**
             * Discount amount format
             */
            private String discountAmountFormat = "&8[&a%{discount}&8]";

            /**
             * Stock unlimited format
             */
            private String stockUnlimited = "&6&lUNLIMITED";
        }

        /**
         * Bazaar gui object
         */
        private Bazaar bazaarGui = new Bazaar();

        /**
         * Bazaar gui arguments class
         */
        @Getter @Setter
        public static class Bazaar extends OkaeriConfig {

            /**
             * return item message
             */
            private String returnItemMessage = "{prefix} &cYou have reached max storage amount &4(%max_amount%) &creturned &4%amount% &citem.";

            /**
             * layout of gui
             */
            @Comment("Layout of gui")
            private List<String> layout = Arrays.asList(
                    "    a    ",
                    " iiiiiii ",
                    "b       n"
            );

            /**
             * name of gui
             */
            private String guiName = "&8Bazaar";

            /**
             * lore modifier
             */
            private String clickLore = "&aClick this for re-take!";

            /**
             * withdraw item title
             */
            private String withdrawTitle = "&6&lWithdraw";

            /**
             * withdraw item subtitle progress
             */
            private String withdrawProgressSubtitle = "&7Withraw in progress...";

            /**
             * withdraw item subtitle success
             */
            private String withdrawSuccessSubtitle = "&aWithdraw success.";

            /**
             * withdraw item subtitle error
             */
            private String withdrawErrorSubtitle = "&cWithdraw error.";

            /**
             * Add item material
             */
            private String material = "GREEN_WOOL";
            /**
             * Add item title
             */
            private String addItemName = "&eAdd item to bazaar";

            /**
             * Add item lore
             */
            private List<String> addItemLore = Arrays.asList("", "&aClick for add item");

            /**
             * Add item gui name
             */
            private String addItemGuiName = "&8Bazaar » Add item";

            /**
             * layout of gui
             */
            @Comment("Layout of gui")
            private List<String> addItemLayout = Arrays.asList(
                    "iiiiiiiii",
                    "iiiiiiiii"
            );
        }

    }
}