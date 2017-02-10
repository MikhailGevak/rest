package askfm.rest;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import askfm.api.ServiceException;

public class ServiceExceptionSerializer implements JsonSerializer<ServiceException> {
	@Override
	public JsonElement serialize(ServiceException exception, Type type, JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("code", exception.getCode());
		jsonObject.addProperty("message", exception.getMessage());
		return jsonObject;
	}
}
