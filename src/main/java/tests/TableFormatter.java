package tests;

/**
 * Formatting HTML tables for article
 */
public class TableFormatter {
    public static void main(String[] args) {
        final String text = "\t10000\t100000\t1000000\t10000000\t100000000\n" +
                "tests.maptests.object.FastUtilObjMapTest\t1720\t3002\t6015\t9360\t13292\n" +
                "tests.maptests.object.HftcMutableObjTest\t1146\t1378\t2928\t6215\t5945\n" +
                "tests.maptests.object.HppcObjMapTest\t1726\t3085\t5692\t9125\t13139\n" +
                "tests.maptests.object.GsObjMapTest\t1566\t2242\t4582\t6012\t8110\n" +
                "tests.maptests.object.JdkMapTest\t1151\t1776\t3759\t5341\t11523\n" +
                "tests.maptests.object.TroveObjMapTest\t2065\t2979\t5713\t10266\t12631\n";
        final String[] lines = text.split("\n");
        final StringBuilder sb = new StringBuilder( 1024 );
        sb.append("<table border=\"1\">\n");
        for ( final String line : lines )
        {
            final String[] parts = line.split("\t");
            sb.append("\t<tr>");
            for ( final String part : parts )
            {
                sb.append( "<td>" );
                sb.append( part.isEmpty() ? "&nbsp;" : part );
                sb.append( "</td>" );
            }
            sb.append("</tr>\n");
        }



        sb.append("</table>");
        System.out.println( sb );
    }
}
