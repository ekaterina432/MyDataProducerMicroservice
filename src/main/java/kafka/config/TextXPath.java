package kafka.config;

import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class TextXPath {

    private final XML xml;

    /**
     * та чать xml, где буду искать необходимыек теги
     */
    private final String node;

    /**
     * @return список тегов внутри определенного тега
     */
    @Override
    public String toString() {
        return this.xml.nodes(this.node)
                .get(0)//получаю первый тег из списка
                .xpath("text()")//eго значение
                .get(0);//первое значение из text
    }

}
