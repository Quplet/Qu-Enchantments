package qu.quEnchantments.util.interfaces;

public interface IItemStack {

    boolean isEnchantmentsDirty();

    void setEnchantmentsDirty(boolean value);

    int corruptedLevel();
}
