package askfm.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;

import com.google.inject.Inject;

import askfm.api.ServiceException;
import askfm.api.exceptions.ManyRequestException;
import askfm.api.exceptions.NoEntityException;
import askfm.api.exceptions.NotValidEntityException;
import askfm.api.ip.IPinfo;
import askfm.api.ip.IpService;
import askfm.api.properties.PropertyService;
import askfm.api.question.CountryFrequencyValidationService;
import askfm.api.question.QuestionService;

@Path("/questions")
public class QuestionResource {
	private static final String DEFAULT_COUNTRY_PROPERTY = "askfm.ipinfo.default";

	private final QuestionService questionService;
	private final IpService ipService;
	private final CountryFrequencyValidationService frequencyValidationService;
	private final String defaultCountry;

	@Inject
	public QuestionResource(QuestionService questionService, IpService ipService,
			CountryFrequencyValidationService frequencyValidationService, PropertyService propertyService) {
		this(questionService, ipService, frequencyValidationService,
				propertyService.getPropertyValue(DEFAULT_COUNTRY_PROPERTY));
	}

	public QuestionResource(QuestionService questionService, IpService ipService,
			CountryFrequencyValidationService frequencyValidationService, String defaultCountry) {
		this.questionService = questionService;
		this.ipService = ipService;
		this.frequencyValidationService = frequencyValidationService;
		this.defaultCountry = defaultCountry;
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return createResponse(() -> questionService.getAll());
	}

	@GET
	@Path("/country/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByCountry(@PathParam("code") String code) {
		return createResponse(() -> questionService.getByCountry(code));
	}

	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Integer id) {
		return createResponse(() -> questionService.getEntityById(id));
	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response create(@Context HttpServletRequest requestContext, String text) throws ServiceException {
		return createResponse(() -> {

			IPinfo ipInfo = ipService.getInfoByIp(requestContext.getRemoteAddr());
			String countryCode = ipInfo.country.code;
			countryCode = StringUtils.isEmpty(countryCode) ? defaultCountry : countryCode;

			if (!frequencyValidationService.doValidation(countryCode))
				throw new ManyRequestException();

			return questionService.createAndSave(text, countryCode);
		});
	}

	private <T> Response createResponse(CreateResponse<T> supplier) {
		try {
			return Response.ok(supplier.get()).build();
		} catch (NoEntityException ex) {
			return Response.status(HttpStatus.BAD_REQUEST_400).entity(ex).build();
		} catch (NotValidEntityException ex) {
			return Response.status(HttpStatus.PRECONDITION_FAILED_412).entity(ex).build();
		} catch (ManyRequestException ex) {
			return Response.status(429).entity(ex).build();
		} catch (ServiceException ex) {
			if (ex.getCause() != null) {
				ex.printStackTrace();
			}
			return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).entity(ex).build();
		}
	}
}
