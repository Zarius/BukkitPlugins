package crussell52.poi.api;

import crussell52.poi.api.IPoiListener;

public interface IPointsOfInterest {
	void registerPoiListener(PoiEvent.Type type, IPoiListener poiListener);
}
