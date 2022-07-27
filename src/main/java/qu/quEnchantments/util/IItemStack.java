package qu.quEnchantments.util;

public interface IItemStack {

    boolean isEnchantmentsDirty();

    void setEnchantmentsDirty(boolean value);

    int corruptedLevel();
}
