package de.chrestin.analysis;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.chrestin.analysis.pojos.MultiBenchmarkJson;
import de.chrestin.analysis.pojos.PerformanceHistoryJson;

public class RestApi {

	private static RestApi singletonRestApi;
	private static final String REST_URI = "https://api.liqid.de/web-api/v1/";
	private Client client;
//  private Client client = ClientBuilder.newClient();
//	private Client client = ClientBuilder.newClient().register(JacksonFeature.class);

	private RestApi() {
		super();
		try {
			this.client = JerseyHttpClientFactory.getJerseyHTTPSClient();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static synchronized RestApi getInstance() {
		if (singletonRestApi == null) {
			singletonRestApi = new RestApi();
		}
		return singletonRestApi;
	}

	public PerformanceHistoryJson getModelPortfolio(String portfolioName) {

		Response response = client //
				.target(REST_URI) //
				.path("model-portfolios/")
				.path(portfolioName)
				.path("/performance") //
				.request(MediaType.APPLICATION_JSON) //
				.get(); //
		
		System.out.println(response.getStatusInfo());
		System.out.println(response.getLength());
		
		PerformanceHistoryJson history = response.readEntity(PerformanceHistoryJson.class);
		// Short pause to prevent REST API to smell a robot (helpful???)
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// Exception could be thrown as well. Only one thread - no interruption possible.
			e.printStackTrace();
		}
		return history;
	}
	
	public MultiBenchmarkJson getBenchmark(int benchmarkId) {

		Response response = client //
				.target(REST_URI) //
				.path("benchmarks/")
				.path("find-by-ids.json")
				.queryParam("benchmark_ids", benchmarkId)//
				.request(MediaType.APPLICATION_JSON) //
				.get(); //
		
		System.out.println(response.getStatusInfo());
		System.out.println(response.getLength());
		
		MultiBenchmarkJson history = response.readEntity(MultiBenchmarkJson.class);
		// Short pause to prevent REST API to smell a robot (helpful???)
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// Exception could be thrown as well. Only one thread - no interruption possible.
			e.printStackTrace();
		}
		return history;
	}

}
