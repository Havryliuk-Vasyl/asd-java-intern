package team.asd.entity;

import java.sql.Timestamp;

public class ChannelPartner {
	private int id;
	private int partyId;
	private String abbreviation;
	private String channelName;
	private String state;
	private Double commission;
	private double bpCommission;
	private boolean fundsHolder;
	private Timestamp version;


	public ChannelPartner(int id, int partyId, String abbreviation, String channelName, String state, Double commission, double bpCommission,
			boolean fundsHolder, Timestamp version) {
		this.id = id;
		this.partyId = partyId;
		this.abbreviation = abbreviation;
		this.channelName = channelName;
		this.state = state;
		this.commission = commission;
		this.bpCommission = bpCommission;
		this.fundsHolder = fundsHolder;
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPartyId() {
		return partyId;
	}

	public void setPartyId(int partyId) {
		this.partyId = partyId;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public double getBpCommission() {
		return bpCommission;
	}

	public void setBpCommission(double bpCommission) {
		this.bpCommission = bpCommission;
	}

	public boolean isFundsHolder() {
		return fundsHolder;
	}

	public void setFundsHolder(boolean fundsHolder) {
		this.fundsHolder = fundsHolder;
	}

	public Timestamp getVersion() {
		return version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}
}
