package repo.ruinspvp.factions.structure.punish;

public enum Punishments {

	CHAT1(1, "Chat Offense 1", 2),
	CHAT2(2, "Chat Offense 2", 12),
	CHAT3(3, "Chat Offense 3" , -1),
	ADVERTISING1(1, "Advertising 1", 6),
	ADVERTISING2(2, "Advertising 2", 24),
	ADVERTISING3(3, "Advertising 3", -1),
	GAMEPLAY1(1, "Gameplay Offense 1", 6),
	GAMEPLAY2(2, "Gameplay Offense 2", 24),
	GAMEPLAY3(3, "Gameplay Offense 3", -1),
	HACKING1(1, "Hacking 1" , 12),
	HACKING2(2, "Hacking 2" , 72),
	HACKING3(3, "Hacking 3" , -1);

	private String punishmentType;
	private int severity;
	private int hours;

	Punishments(int severity, String title, int hours) {
		this.severity = severity;
		this.punishmentType = title;
		this.hours = hours;
	}

	public int getSeverity() {
		return this.severity;
	}

	public String getTitle() {
		return this.punishmentType;
	}

	public int getHours() {
		return this.hours;
	}
}



