package twilightforest.item.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.config.TFAreYouDoing;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.Arrays;
import java.util.function.BooleanSupplier;

public class ConditionFactoryConfig implements IConditionFactory {
    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        Property property = TFAreYouDoing
                .iJustNeedThisConfig(JsonUtils.getString(json, "mod"), JsonUtils.getString(json, "config"))
                .getCategory(JsonUtils.getString(json, "category", "general"))
                .get(JsonUtils.getString(json, "key"));

        if (property == null) {
            throw new JsonParseException("Property not found!");
        }

        // These lists will be listed off as [thing,stuff,what] or [true,false,true] or [12.34,5.678,9.0].
        // Required is a starting square bracket, enlistment of items separated by commas, and ending square bracket.
        // Spaces will be treated as part of the 'String' and thus might be invalid for non-String lists.

        // Otherwise non-listed stuff can just be presented as is, but will be case-sensitive.
        if (property.isList()) {
            switch (property.getType()) {
                case STRING:
                    return () -> Arrays.toString(property.getStringList()).equals(JsonUtils.getString(json, "value"));
                case INTEGER:
                    return () -> Arrays.toString(property.getIntList()).equals(JsonUtils.getString(json, "value"));
                case BOOLEAN:
                    return () -> Arrays.toString(property.getBooleanList()).equals(JsonUtils.getString(json, "value"));
                case DOUBLE:
                    return () -> Arrays.toString(property.getDoubleList()).equals(JsonUtils.getString(json, "value"));
            }
        } else {
            switch (property.getType()) {
                case STRING:
                    return () -> JsonUtils.getString(json, "value").equals(property.getString());
                case INTEGER:
                    return () -> JsonUtils.getString(json, "value").equals(Integer.toString(property.getInt()));
                case BOOLEAN:
                    return () -> JsonUtils.getString(json, "value").equals(Boolean.toString(property.getBoolean()));
                case DOUBLE:
                    return () -> JsonUtils.getString(json, "value").equals(Double.toString(property.getDouble()));
            }
        }

        throw new JsonParseException("The property requested has an invalid type!");
    }
}
